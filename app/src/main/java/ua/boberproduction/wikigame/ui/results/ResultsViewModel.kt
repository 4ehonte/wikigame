package ua.boberproduction.wikigame.ui.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.AnkoLogger
import ua.boberproduction.wikigame.repository.PreferencesProvider
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.util.SchedulerProvider
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
        private val repository: Repository,
        private val schedulerProvider: SchedulerProvider,
        private val preferencesProvider: PreferencesProvider) : ViewModel(), AnkoLogger {

    val clicks = MutableLiveData<Int>()
    val seconds = MutableLiveData<Int>()

    fun onCreate(clicks: Int, seconds: Int) {
        this.clicks.value = clicks
        this.seconds.value = seconds
    }
}