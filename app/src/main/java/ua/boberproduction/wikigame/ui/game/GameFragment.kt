package ua.boberproduction.wikigame.ui.game

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
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

        val startPhrase = arguments!!.getString(PregameFragment.START_PHRASE)
        val targetPhrase = arguments!!.getString(PregameFragment.TARGET_PHRASE)
        viewModel.onCreate(startPhrase to targetPhrase)
    }
}