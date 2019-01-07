package ua.boberproduction.wikigame.mvvm.menu

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment.findNavController
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentMainMenuBinding
import android.content.Intent
import android.net.Uri
import org.chromium.base.BuildInfo.getPackageName


class MainMenuFragment : BaseFragment() {
    lateinit var binding: FragmentMainMenuBinding
    lateinit var viewModel: MainMenuViewModel

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main_menu, container, false)
        binding.setLifecycleOwner(this)

        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel = getViewModel()
        binding.viewModel = viewModel

        viewModel.showPregame.observe(this, Observer {
            findNavController(this).navigate(R.id.action_mainMenuFragment_to_pregameFragment)
        })

        viewModel.showStatistics.observe(this, Observer {
            findNavController(this).navigate(R.id.action_mainMenuFragment_to_statisticsFragment)
        })

        viewModel.showSettings.observe(this, Observer {
            findNavController(this).navigate(R.id.action_mainMenuFragment_to_settingsFragment)
        })

        viewModel.showPlayStore.observe(this, Observer {
            openPlayStore()
        })
    }

    private fun openPlayStore() {
        val appPackageName = getPackageName(activity) // getPackageName() from Context or Activity object
        try {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=$appPackageName")))
        } catch (anfe: android.content.ActivityNotFoundException) {
            startActivity(Intent(Intent.ACTION_VIEW, Uri.parse("https://play.google.com/store/apps/details?id=$appPackageName")))
        }

    }

}