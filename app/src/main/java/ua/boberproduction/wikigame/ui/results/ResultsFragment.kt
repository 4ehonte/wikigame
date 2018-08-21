package ua.boberproduction.wikigame.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentResultsBinding
import ua.boberproduction.wikigame.ui.game.GameViewModel

class ResultsFragment : BaseFragment() {
    lateinit var binding: FragmentResultsBinding
    lateinit var viewModel: GameViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

}