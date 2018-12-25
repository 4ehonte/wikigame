package ua.boberproduction.wikigame.mvvm.postgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.repository.ResourcesRepository
import ua.boberproduction.wikigame.util.Levels
import ua.boberproduction.wikigame.util.SchedulerProvider
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
        private val dataRepository: DataRepository,
        private val schedulerProvider: SchedulerProvider,
        private val preferencesRepository: PreferencesRepository,
        resourcesRepository: ResourcesRepository) : ViewModel(), AnkoLogger {

    lateinit var result: Result
    val clicks = MutableLiveData<Int>()
    val time = MutableLiveData<String>()
    val totalBeforeAndNow = MutableLiveData<Pair<Int, Int>>()
    val points = MutableLiveData<Int>()
    val levels = Levels(resourcesRepository)

    fun onCreate(result: Result) {
        this.result = result

        clicks.value = result.clicks
        time.value = getFormattedTime(result.seconds)
        points.value = result.getPoints()

        doAsync { dataRepository.saveResult(result) }
        savePoints(result.getPoints())
        calculateLevelCompletionPercent()
    }

    /**
     * Get time in human readable format (mm:ss)
     */
    private fun getFormattedTime(seconds: Int): String {
        val minutes = seconds / 60
        val secondsLeft = seconds % 60
        return String.format("%02d:%02d", minutes, secondsLeft)
    }

    private fun savePoints(points: Int) {
        val previousTotalPoints = preferencesRepository.getTotalPoints()
        val newTotalPoints = previousTotalPoints + points
        totalBeforeAndNow.value = previousTotalPoints to newTotalPoints
        preferencesRepository.setTotalPoints(newTotalPoints)
    }

    private fun calculateLevelCompletionPercent() {

    }
}