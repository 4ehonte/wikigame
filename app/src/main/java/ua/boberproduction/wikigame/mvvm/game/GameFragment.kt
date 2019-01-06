package ua.boberproduction.wikigame.mvvm.game

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_game.*
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.debug
import timber.log.Timber
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.MainActivity
import ua.boberproduction.wikigame.OnBackPressListener
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentGameBinding
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.mvvm.pregame.PregameFragment

class GameFragment : BaseFragment(), OnBackPressListener {
    private lateinit var binding: FragmentGameBinding
    lateinit var viewModel: GameViewModel
    private var creationTime = 0L

    companion object {
        const val RESULTS = "results"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        creationTime = System.currentTimeMillis()
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Timber.d("GameFragment init time: ${System.currentTimeMillis() - creationTime}")
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        (activity!! as MainActivity).backPressListener = this

        viewModel = getViewModel()
        binding.viewModel = viewModel

        configureWebView()

        val startPhrase = arguments!!.getString(PregameFragment.START_PHRASE)
        val targetPhrase = arguments!!.getString(PregameFragment.TARGET_PHRASE)

        if (savedInstanceState == null)
            viewModel.onCreate(startPhrase to targetPhrase)

        viewModel.loadUrl.observe(this, Observer {
            if (!it.isNullOrEmpty()) loadArticle(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) showError(it)
        })

        viewModel.showResults.observe(this, Observer {
            if (it != null) showResultsFragment(it)
        })
    }

    private fun showResultsFragment(result: Result) {
        val bundle = bundleOf(RESULTS to result)
        NavHostFragment.findNavController(this).navigate(R.id.action_gameFragment_to_resultsFragment, bundle)
    }

    @SuppressLint("SetJavaScriptEnabled")
    private fun configureWebView() {
        webview.webViewClient = object : WebViewClient() {
            // override link clicks.
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                viewModel.onLinkClicked(url)
                return true
            }

            override fun onPageFinished(view: WebView?, url: String) {
                hideWebPageElements()
                viewModel.pageLoaded(url, webview.title)
            }
        }

        webview.settings.javaScriptEnabled = true
        webview.settings.cacheMode = WebSettings.LOAD_CACHE_ELSE_NETWORK
    }

    // After the page has loaded, hide header, footer, and other irrelevant elements
    private fun hideWebPageElements() {
        webview.loadUrl("javascript:(function() { " +
                "document.getElementsByClassName('header-container')[0].style.display='none'; " +
                "document.getElementsByClassName('minerva-footer')[0].style.display='none'; " +
                "document.getElementsByClassName('pre-content')[0].style.display='none'; " +
                "})()")
    }

    private fun loadArticle(url: String) {
        if (webview.url != url) webview.loadUrl(url)
    }

    override fun onBackPressed(): Boolean {
        return if (isVisible && webview.canGoBack()) {
            webview.stopLoading()
            webview.goBack()
            true
        } else false
    }

    override fun onDestroyView() {
        webview.stopLoading()
        webview.destroy()

        super.onDestroyView()
    }
}