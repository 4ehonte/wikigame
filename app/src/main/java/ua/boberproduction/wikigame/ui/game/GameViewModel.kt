package ua.boberproduction.wikigame.ui.game

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.repository.PreferencesProvider
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.util.SchedulerProvider
import ua.boberproduction.wikigame.util.SingleLiveEvent
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class GameViewModel @Inject constructor(
        private val repository: Repository,
        private val schedulerProvider: SchedulerProvider,
        private val preferencesProvider: PreferencesProvider,
        private val app: Application) : AndroidViewModel(app), AnkoLogger {

    val url = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val target = MutableLiveData<String>()
    val clicksCounter = MutableLiveData<Int>()
    val time = MutableLiveData<String>()
    val errorMessage = SingleLiveEvent<String>()

    private val disposables = CompositeDisposable()
    private var timer: Observable<Long>? = null

    init {
        clicksCounter.value = 0

    }

    fun onCreate(phrases: Pair<String, String>) {
        val locale = preferencesProvider.getAppLocale()

        val url = "https://$locale.m.wikipedia.org/wiki/${phrases.first}"
        this.url.postValue(url)
        this.title.value = phrases.first
        this.target.value = phrases.second
        this.clicksCounter.value = 0
        this.time.value = "00:00"
    }

    fun onLinkClicked(url: String) {
        if (url.contains("wikipedia.org/wiki")) {
            this.url.postValue(url)
            val title = url.substringAfterLast("/").replace("_", " ")
            this.title.value = title
            clicksCounter.value = clicksCounter.value!! + 1
        } else {
            errorMessage.postValue(app.getString(R.string.error_external_link))
        }
    }

    fun pageLoaded() {
        if (timer == null) startTimer()
    }

    private fun startTimer() {
        timer = Observable.interval(1, TimeUnit.SECONDS)
        disposables.add(
                timer!!.subscribeOn(schedulerProvider.io())
                        .observeOn(schedulerProvider.mainThread())
                        .subscribe { time.value = getFormattedTime(it) })
    }

    // Convert seconds into readable format (mm:ss)
    private fun getFormattedTime(seconds: Long): String {
        val minutes = seconds / 60
        val secondsLeft = seconds % 60
        return String.format("%02d:%02d", minutes, secondsLeft)
    }

    fun destroy() {
        disposables.dispose()
    }
}