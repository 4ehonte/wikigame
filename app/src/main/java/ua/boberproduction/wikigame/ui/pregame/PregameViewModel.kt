package ua.boberproduction.wikigame.ui.pregame

import androidx.lifecycle.ViewModel
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import ua.boberproduction.wikigame.repository.PreferencesProvider
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.util.SchedulerProvider
import ua.boberproduction.wikigame.util.SingleLiveEvent
import javax.inject.Inject

class PregameViewModel @Inject constructor(
        private val repository: Repository,
        private val schedulerProvider: SchedulerProvider,
        private val preferencesProvider: PreferencesProvider) : ViewModel() {

    val disposables = CompositeDisposable()
    val errorMessage = SingleLiveEvent<String>()
    val phrases = SingleLiveEvent<Pair<String, String>>()
    val startGame = SingleLiveEvent<Unit>()

    fun onCreate() {
        loadPhrases()
    }

    /**
     * Load two phrases from the repository (a phrase to begin game from, and a target phrase) into a LiveData object
     */
    private fun loadPhrases() {
        val userLevel = preferencesProvider.getUserLevel()

        disposables.add(
                repository.getPhrases(userLevel)
                        .observeOn(schedulerProvider.mainThread())
                        .subscribeOn(schedulerProvider.io())
                        .subscribe({ phrases ->
                            this.phrases.postValue(phrases)
                        }, { error ->
                            errorMessage.postValue(error.message)
                        }))
    }

    fun startButtonClicked() = startGame.call()
}