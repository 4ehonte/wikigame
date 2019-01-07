package ua.boberproduction.wikigame.mvvm.statistics

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.addTo
import io.reactivex.rxkotlin.subscribeBy
import timber.log.Timber
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.repository.ResourcesRepository
import ua.boberproduction.wikigame.util.Levels
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.util.SchedulerProvider
import ua.boberproduction.wikigame.util.SingleLiveEvent
import ua.boberproduction.wikigame.util.getReadableTime
import javax.inject.Inject

class StatisticsViewModel @Inject constructor(
        private val dataRepository: DataRepository,
        private val preferencesRepository: PreferencesRepository,
        private val resourcesRepository: ResourcesRepository,
        private val schedulerProvider: SchedulerProvider) : ViewModel() {

    private val disposables = CompositeDisposable()
    private val levels = Levels(resourcesRepository)

    val userLevel = MutableLiveData<Int>()
    val points = MutableLiveData<Int>()
    val clicks = MutableLiveData<Int>()
    val showHistory = SingleLiveEvent<Unit>()
    val totalTime = MutableLiveData<String>()
    val errorMessage = SingleLiveEvent<String>()
    val results by lazy { MutableLiveData<List<Result>>() }

    fun start() {
        val userPoints = preferencesRepository.getTotalPoints()
        userLevel.value = levels.getCurrentLevel(userPoints)
        points.value = userPoints

        loadTotalClicks()
        loadTotalTime()
    }

    private fun loadTotalTime() {
        dataRepository.getTotalTime()
                .subscribeOn(schedulerProvider.io())
                .map { getReadableTime(it) }
                .subscribeBy(
                        onSuccess = { totalTime.postValue(it) },
                        onError = {
                            totalTime.postValue("00:00")
                            Timber.e(it)
                            errorMessage.postValue(it.message)
                        }
                ).addTo(disposables)
    }

    private fun loadTotalClicks() {
        dataRepository.getTotalClicks()
                .subscribeOn(schedulerProvider.io())
                .subscribeBy(
                        onSuccess = { clicks.postValue(it) },
                        onError = {
                            clicks.postValue(0)
                            Timber.e(it)
                            errorMessage.postValue(it.message)
                        }
                ).addTo(disposables)
    }

    private fun loadResults() {
        dataRepository.getResults()
                .subscribeOn(schedulerProvider.io())
                .subscribeBy(
                        onSuccess = { results.postValue(it) },
                        onError = { errorMessage.postValue(it.message) }
                ).addTo(disposables)
    }

    fun historyBtnClicked() = showHistory.call()

    override fun onCleared() {
        super.onCleared()
        disposables.dispose()
    }
}