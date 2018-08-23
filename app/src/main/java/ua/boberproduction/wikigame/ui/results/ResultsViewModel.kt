package ua.boberproduction.wikigame.ui.results

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import org.jetbrains.anko.AnkoLogger
import org.jetbrains.anko.doAsync
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.repository.PreferencesProvider
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.util.SchedulerProvider
import javax.inject.Inject

class ResultsViewModel @Inject constructor(
        private val repository: Repository,
        private val schedulerProvider: SchedulerProvider,
        private val preferencesProvider: PreferencesProvider) : ViewModel(), AnkoLogger {

    lateinit var result: Result
    val clicks = MutableLiveData<Int>()
    val time = MutableLiveData<String>()

    fun onCreate(result: Result) {
        this.result = result

        this.clicks.value = result.clicks
        this.time.value = getFormattedTime(result.seconds)

        doAsync { repository.saveResult(result) }
    }

    /**
     * Get time in human readable format (mm:ss)
     */
    private fun getFormattedTime(seconds: Int): String {
        val minutes = seconds / 60
        val secondsLeft = seconds % 60
        return String.format("%02d:%02d", minutes, secondsLeft)
    }
}