package ua.boberproduction.wikigame.mvvm.pregame

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.text.Html
import android.text.method.ScrollingMovementMethod
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_info_dialog.*
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentInfoDialogBinding
import ua.boberproduction.wikigame.di.ViewModelFactory
import javax.inject.Inject

class InfoDialogFragment : DialogFragment(), HasSupportFragmentInjector {
    @Inject
    lateinit var viewModelFactory: ViewModelFactory
    @Inject
    lateinit var childFragmentInjector: DispatchingAndroidInjector<Fragment>
    lateinit var viewModel: PregameViewModel
    private lateinit var binding: FragmentInfoDialogBinding
    private lateinit var phrase: String

    companion object {
        const val TAG = "info dialog fragment"
        const val PARAM_PHRASE = "phrase"
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = ViewModelProviders.of(activity!!, viewModelFactory).get(PregameViewModel::class.java)

        binding.viewModel = viewModel

        summary_container.contentView = summary_tv
        close_btn.setOnClickListener { dismiss() }
        summary_tv.movementMethod = ScrollingMovementMethod()

        phrase = arguments?.getString(PARAM_PHRASE).orEmpty()
        viewModel.loadInfo(phrase)

        viewModel.phraseInfo.observe(this, Observer {
            if (it.first == phrase) {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                    summary_tv.text = Html.fromHtml(it.second, Html.FROM_HTML_MODE_LEGACY)
                } else {
                    @Suppress("DEPRECATION")
                    summary_tv.text = Html.fromHtml(it.second)
                }
            }
            summary_container.showContentView()
        })
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_info_dialog, container, false)
        binding.setLifecycleOwner(this)

        isCancelable = true

        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)

        return binding.root
    }


    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment>? {
        return childFragmentInjector
    }
}