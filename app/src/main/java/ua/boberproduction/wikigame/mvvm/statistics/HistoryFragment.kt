package ua.boberproduction.wikigame.mvvm.statistics

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_history.*
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentHistoryBinding
import ua.boberproduction.wikigame.databinding.FragmentStatisticsBinding

class HistoryFragment : BaseFragment() {
    lateinit var binding: FragmentHistoryBinding
    lateinit var viewModel: StatisticsViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_history, container, false)
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()
        binding.viewModel = viewModel

        viewModel.loadResults()

        back_button.setOnClickListener {
            findNavController().navigateUp()
        }

        history_rv.layoutManager = LinearLayoutManager(activity!!)

        viewModel.results.observe(this, Observer {
            history_rv.adapter = HistoryAdapter(it)
        })
    }
}