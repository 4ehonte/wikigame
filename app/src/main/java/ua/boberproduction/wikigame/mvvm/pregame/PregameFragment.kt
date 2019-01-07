package ua.boberproduction.wikigame.mvvm.pregame

import android.animation.Animator
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.OvershootInterpolator
import android.webkit.WebSettings
import android.webkit.WebView
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_pregame.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.MainActivity
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentPregameBinding
import ua.boberproduction.wikigame.ioScope
import ua.boberproduction.wikigame.util.showDialogFragment

class PregameFragment : BaseFragment() {
    lateinit var binding: FragmentPregameBinding
    lateinit var viewModel: PregameViewModel

    companion object {
        const val START_PHRASE = "start_phrase"
        const val TARGET_PHRASE = "target_phrase"
        const val BUTTON_ANIMATION_DURATION = 400L
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_pregame, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel()
        binding.viewModel = viewModel

        phrase_start.animationDuration = 1200
        phrase_start.animationDelay = 100

        phrase_target.animationDuration = 1200
        phrase_target.animationDelay = 100

        phrase_target.addAnimatorListener(object : Animator.AnimatorListener {
            override fun onAnimationRepeat(p0: Animator?) {}
            override fun onAnimationEnd(p0: Animator?) {
                if (isResumed) showButtons()
            }

            override fun onAnimationStart(p0: Animator?) {}
            override fun onAnimationCancel(p0: Animator?) {}
        })

        viewModel.onCreate()

        viewModel.errorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) showError(it)
        })

        viewModel.phrases.observe(this, Observer { pair ->
            phrase_start.text = pair.first
            phrase_target.text = pair.second
        })

        viewModel.startGame.observe(this, Observer {
            startGame()
        })

        viewModel.showInfoWindow.observe(this, Observer {
            showInfoDialog(it)
        })
    }

    private fun startGame() {
        val bundle = bundleOf(
                START_PHRASE to viewModel.phrases.value?.first,
                TARGET_PHRASE to viewModel.phrases.value?.second)

        NavHostFragment.findNavController(this)
                .navigate(R.id.action_pregameFragment_to_gameFragment, bundle)
    }

    // Reveal buttons with overshoot animation
    private fun showButtons() {
        val scaleXStart = getButtonAnimator(btn_start, "scaleX")
        val scaleYStart = getButtonAnimator(btn_start, "scaleY")

        val scaleXQuestion = getButtonAnimator(target_info_btn, "scaleX")
        val scaleYQuestion = getButtonAnimator(target_info_btn, "scaleY")

        val set = AnimatorSet()
        set.playTogether(scaleXStart, scaleYStart, scaleXQuestion, scaleYQuestion)
        set.start()
    }

    private fun getButtonAnimator(button: View, propertyName: String): ObjectAnimator {
        val animator = ObjectAnimator.ofFloat(button, propertyName, 0f, 1f).setDuration(BUTTON_ANIMATION_DURATION)
        animator.interpolator = OvershootInterpolator()
        return animator
    }

    // Show dialog fragment containing summary from Wikipedia article on given phrase
    private fun showInfoDialog(phrase: String) {
        val dialogFragment = InfoDialogFragment()
        val bundle = bundleOf(InfoDialogFragment.PARAM_PHRASE to phrase)
        dialogFragment.arguments = bundle
        (activity as MainActivity).showDialogFragment(InfoDialogFragment.TAG, dialogFragment)
    }
}