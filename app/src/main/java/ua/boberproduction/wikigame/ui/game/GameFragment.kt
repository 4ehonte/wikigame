package ua.boberproduction.wikigame.ui.game

import android.graphics.Bitmap
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebView
import android.webkit.WebViewClient
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import im.delight.android.webview.AdvancedWebView
import kotlinx.android.synthetic.main.fragment_game.*
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentGameBinding
import ua.boberproduction.wikigame.ui.pregame.PregameFragment

class GameFragment : BaseFragment() {
    lateinit var binding: FragmentGameBinding
    lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()
        binding.viewModel = viewModel

        configureWebView()

        val startPhrase = arguments!!.getString(PregameFragment.START_PHRASE)
        val targetPhrase = arguments!!.getString(PregameFragment.TARGET_PHRASE)

        viewModel.onCreate(startPhrase to targetPhrase)
        viewModel.url.observe(this, Observer {
            if (!it.isNullOrEmpty()) loadArticle(it)
        })

        viewModel.errorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) showError(it)
        })
    }

    private fun configureWebView() {
        // override link clicks.
        webview.webViewClient = object : WebViewClient() {
            override fun shouldOverrideUrlLoading(view: WebView?, url: String): Boolean {
                viewModel.onLinkClicked(url)
                return true
            }
        }

        webview.setListener(activity, object : AdvancedWebView.Listener {
            override fun onPageFinished(url: String?) {
                hideWebPageElements()
                viewModel.pageLoaded()
            }

            override fun onPageError(errorCode: Int, description: String?, failingUrl: String?) {
            }

            override fun onDownloadRequested(url: String?, suggestedFilename: String?, mimeType: String?, contentLength: Long, contentDisposition: String?, userAgent: String?) {
            }

            override fun onExternalPageRequest(url: String?) {
            }

            override fun onPageStarted(url: String?, favicon: Bitmap?) {
            }

        })
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

    override fun onDestroy() {
        viewModel.destroy()
        super.onDestroy()
    }
}