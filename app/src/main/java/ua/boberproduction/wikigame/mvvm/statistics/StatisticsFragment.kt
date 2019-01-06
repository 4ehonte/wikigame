package ua.boberproduction.wikigame.mvvm.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentStatisticsBinding

class StatisticsFragment : BaseFragment() {
    lateinit var binding: FragmentStatisticsBinding
    lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_statistics, container, false)
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()
        binding.viewModel = viewModel

        viewModel.start()
    }
}