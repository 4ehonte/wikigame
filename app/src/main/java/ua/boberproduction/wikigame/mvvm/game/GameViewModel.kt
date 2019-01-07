package ua.boberproduction.wikigame.mvvm.game

import android.app.Application
import androidx.annotation.VisibleForTesting
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import org.jetbrains.anko.AnkoLogger
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.repository.ResourcesRepository
import ua.boberproduction.wikigame.util.Levels
import ua.boberproduction.wikigame.util.SchedulerProvider
import ua.boberproduction.wikigame.util.SingleLiveEvent
import ua.boberproduction.wikigame.util.Timer
import java.net.URLDecoder
import javax.inject.Inject

class GameViewModel @Inject constructor(
        private val dataRepository: DataRepository,
        private val schedulerProvider: SchedulerProvider,
        private val resourcesRepository: ResourcesRepository,
        private val preferencesRepository: PreferencesRepository,
        private val app: Application) : AndroidViewModel(app), AnkoLogger {

    val loadUrl = MutableLiveData<String>()
    val title = MutableLiveData<String>()
    val target = MutableLiveData<String>()
    val clicksCounter = MutableLiveData<Int>()
    val goToMainMenu = SingleLiveEvent<Unit>()
    val time = MutableLiveData<String>()
    val showMenu = MutableLiveData<Boolean>()
    val errorMessage = SingleLiveEvent<String>()
    val progressBarVisibility = SingleLiveEvent<Boolean>()
    val showResults = SingleLiveEvent<Result>()

    // game phrases: initial and target phrase.
    lateinit var phrases: Pair<String, String>
    private val disposables = CompositeDisposable()
    var timer: Timer? = null

    init {
        clicksCounter.value = 0

    }

    fun onCreate(phrases: Pair<String, String>) {
        this.phrases = phrases
        val locale = preferencesRepository.getAppLocale()

        val url = "https://$locale.m.wikipedia.org/wiki/${phrases.first}"
        timer?.stop()
        timer = null
        this.loadUrl.postValue(url)
        this.title.value = phrases.first
        progressBarVisibility.value = true
        this.target.value = phrases.second
        this.clicksCounter.value = 0
        this.time.value = "00:00"
    }

    @VisibleForTesting
    internal fun initTimer() {
        if (timer == null) timer = Timer()
        else timer!!.stop()

        disposables.add(
                timer!!.observeOn(AndroidSchedulers.mainThread())
                        .subscribe { time.value = timer?.getFormattedTime().orEmpty() })
    }

    fun onLinkClicked(url: String) {
        // check if the clicked link is a valid wiki link
        if (url.contains("wikipedia.org/wiki")) {
            // increment the clicks counter
            clicksCounter.value = clicksCounter.value!! + 1

            // extract the title
            val title = getPageTitle(url)

            // check if it matches the target title. If it does, finish the game.
            if (title == phrases.second) {
                finishGame()
                return
            }
            // if it didn't match the target, show the article title and start loading the web page.
            this.title.value = title
            progressBarVisibility.value = true

            timer?.pause()
            this.loadUrl.postValue(url)
        } else {
            errorMessage.postValue(app.getString(R.string.error_external_link))
        }
    }

    private fun getPageTitle(url: String): String {
        // get page title from a page URL like this; https://en.wikipedia.org/wiki/Percent-encoding
        val encodedTitle = url.substringAfterLast("/").replace("_", " ")
        return URLDecoder.decode(encodedTitle, "UTF-8")
    }

    fun pageLoaded(url: String, title: String) {
        progressBarVisibility.value = false

        if (timer == null) initTimer() else timer?.resume()

        // separate the actual title (etc. "Barcelona" from "Barcelona — Wikipedia")
        this.title.value = title.substringBeforeLast(" -")
    }

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }

    fun finishGame() {
        timer?.stop()

        val levels = Levels(resourcesRepository)
        val userLevel = levels.getUserLevel(preferencesRepository.getTotalPoints()).levelNumber
        val result = Result(userLevel, phrases.first, phrases.second, clicksCounter.value
                ?: 0, timer?.time?.toInt() ?: 0, System.currentTimeMillis())
        showResults.value = result
    }

    private fun showMenuDialog() {
        if (showMenu.value != true) {
            timer?.pause()
            showMenu.value = true
        }
    }

    fun onMenuClick() {
        showMenuDialog()
    }

    fun onMainMenuBtnClicked() {
        showMenu.value = false
        goToMainMenu.call()
    }

    fun onMenuClosed() {
        showMenu.value = false
        timer?.resume()
    }

    fun onStop() {
        if (timer?.isRunning() == true) {
            showMenuDialog()
        }
    }

}