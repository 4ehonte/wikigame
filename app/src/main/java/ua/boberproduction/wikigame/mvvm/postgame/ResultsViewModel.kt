package ua.boberproduction.wikigame.mvvm.postgame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.*
import org.jetbrains.anko.AnkoLogger
import ua.boberproduction.wikigame.ioScope
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.repository.ResourcesRepository
import ua.boberproduction.wikigame.util.Levels
import ua.boberproduction.wikigame.util.SingleLiveEvent
import ua.boberproduction.wikigame.util.getReadableTime
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
        private val dataRepository: DataRepository,
        private val preferencesRepository: PreferencesRepository,
        resourcesRepository: ResourcesRepository) : ViewModel(), AnkoLogger {

    lateinit var result: Result
    val clicks = MutableLiveData<Int>()
    val time = MutableLiveData<String>()
    val totalBeforeAndNow = MutableLiveData<Pair<Int, Int>>()
    val points = MutableLiveData<Int>()
    val levels = Levels(resourcesRepository)
    val playAgain = SingleLiveEvent<Unit>()
    val goToMainMenu = SingleLiveEvent<Unit>()

    fun onCreate(result: Result) {
        this.result = result

        clicks.value = result.clicks
        time.value = getReadableTime(result.seconds)
        points.value = result.getPoints()

        ioScope.launch { dataRepository.saveResult(result) }

        savePoints(result.getPoints())
    }


    private fun savePoints(points: Int) {
        val previousTotalPoints = preferencesRepository.getTotalPoints()
        val newTotalPoints = previousTotalPoints + points
        totalBeforeAndNow.value = previousTotalPoints to newTotalPoints
        preferencesRepository.setTotalPoints(newTotalPoints)
    }

    fun playAgainBtnClicked() = playAgain.call()

    fun menuBtnClicked() = goToMainMenu.call()

}