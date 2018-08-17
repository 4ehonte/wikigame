package ua.boberproduction.wikigame.ui.pregame

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_pregame.*
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentPregameBinding

class PregameFragment : BaseFragment() {
    lateinit var binding: FragmentPregameBinding
    lateinit var viewModel: PregameViewModel

    companion object {
        const val START_PHRASE = "start_phrase"
        const val TARGET_PHRASE = "target_phrase"
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

        viewModel.onCreate()

        viewModel.errorMessage.observe(this, Observer {
            if (!it.isNullOrEmpty()) showError(it)
        })

        viewModel.phrases.observe(this, Observer {
            if (it != null) {
                phrase_start.text = it.first
                phrase_target.text = it.second
            }
        })

        viewModel.startGame.observe(this, Observer {
            val bundle = bundleOf(
                    START_PHRASE to viewModel.phrases.value?.first,
                    TARGET_PHRASE to viewModel.phrases.value?.second)
            NavHostFragment.findNavController(this).navigate(R.id.action_pregameFragment_to_gameFragment, bundle)
        })
    }
}