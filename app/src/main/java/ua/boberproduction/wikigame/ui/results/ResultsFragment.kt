package ua.boberproduction.wikigame.ui.results

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentResultsBinding
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.ui.game.GameFragment

class ResultsFragment : BaseFragment() {
    lateinit var binding: FragmentResultsBinding
    lateinit var viewModel: ResultsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel()
        binding.viewModel = viewModel

        val result = arguments!!.getParcelable<Result>(GameFragment.RESULTS)
        viewModel.onCreate(result)
    }
}