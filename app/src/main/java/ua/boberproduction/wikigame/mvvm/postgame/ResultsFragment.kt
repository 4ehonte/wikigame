package ua.boberproduction.wikigame.mvvm.postgame

import android.media.MediaPlayer
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.navigation.fragment.NavHostFragment
import kotlinx.android.synthetic.main.fragment_results.*
import ua.boberproduction.wikigame.BaseFragment
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.databinding.FragmentResultsBinding
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.mvvm.game.GameFragment
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.repository.ResourcesRepository
import ua.boberproduction.wikigame.util.Levels
import ua.boberproduction.wikigame.util.views.CircularLevelView
import java.io.File
import java.io.IOException
import javax.inject.Inject

class ResultsFragment : BaseFragment(), CircularLevelView.EventListener {
    lateinit var binding: FragmentResultsBinding
    lateinit var viewModel: ResultsViewModel
    @Inject
    lateinit var resourcesRepository: ResourcesRepository
    @Inject
    lateinit var preferencesRepository: PreferencesRepository

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_results, container, false)
        binding.setLifecycleOwner(this)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        viewModel = getViewModel()
        binding.viewModel = viewModel

        points_tv.text = getString(R.string.points, 0)

        val result = arguments!!.getParcelable<Result>(GameFragment.RESULTS)
                ?: throw IllegalStateException("Result argument must be set.")

        viewModel.onCreate(result)

        val totalPoints = preferencesRepository.getTotalPoints()

        viewModel.totalBeforeAndNow.observe(this, Observer {
            level_circular_view.listener = this
            level_circular_view.levels = Levels(resourcesRepository)
            level_circular_view.startPoints = totalPoints
            level_circular_view.newPoints = totalPoints + result.getPoints()
            level_circular_view.startAnimation()
        })

        viewModel.points.observe(this, Observer {
            points_tv.text = getString(R.string.points, it)
        })

        viewModel.playAgain.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_resultsFragment_to_pregameFragment)
        })

        viewModel.goToMainMenu.observe(this, Observer {
            NavHostFragment.findNavController(this).navigate(R.id.action_resultsFragment_to_mainMenuFragment)
        })
    }

    override fun onLevelUp(level: Int) {
        playSound("ding.wav")
    }

    private fun playSound(assetFileName: String) {
        try {
            val afd = activity!!.assets.openFd("sounds" + File.separator + assetFileName)
            val player = MediaPlayer()
            player.setDataSource(afd.fileDescriptor, afd.startOffset, afd.length)
            player.prepare()
            player.start()
        } catch (e: IOException) {
            e.printStackTrace()
        }
    }
}