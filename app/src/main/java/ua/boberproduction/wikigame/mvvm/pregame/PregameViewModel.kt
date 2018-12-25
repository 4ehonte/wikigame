package ua.boberproduction.wikigame.mvvm.pregame

import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.util.SchedulerProvider
import ua.boberproduction.wikigame.util.SingleLiveEvent
import ua.boberproduction.wikigame.util.randomItem
import java.util.*
import javax.inject.Inject

class PregameViewModel @Inject constructor(
        private val dataRepository: DataRepository,
        private val schedulerProvider: SchedulerProvider,
        private val preferencesRepository: PreferencesRepository) : ViewModel() {

    private val disposables = CompositeDisposable()
    val errorMessage = SingleLiveEvent<String>()
    val phrases = SingleLiveEvent<Pair<String, String>>()
    val startGame = SingleLiveEvent<Unit>()

    fun onCreate() {
        loadPhrases()
    }

    /**
     * Load two phrases from the dataRepository (a phrase to begin game from, and a target phrase) into a LiveData object
     */
    private fun loadPhrases() {
        val userLevel = preferencesRepository.getUserLevel()

        disposables.add(
                dataRepository.getPhrases(userLevel)
                        .observeOn(schedulerProvider.mainThread())
                        .subscribeOn(schedulerProvider.io())
                        .map { phrasesList -> getRandomPhrases(phrasesList) }
                        .subscribe({ phrases ->
                            this.phrases.postValue(phrases)
                        }, { error ->
                            errorMessage.postValue(error.message)
                        }))
    }

    /**
     * Gets a pair of randomItem phrases from a given list
     */
    internal fun getRandomPhrases(phrasesList: List<String>): Pair<String, String> {
        if (phrasesList.size < 2) throw IllegalArgumentException("Phrases list must contain at least two phrases.")

        val mutableList = phrasesList.toMutableList()
        val random = Random()
        val firstPhrase = mutableList.randomItem(random)
        mutableList.remove(firstPhrase)
        val secondPhrase = mutableList.randomItem(random)

        return firstPhrase!! to secondPhrase!!
    }

    fun startButtonClicked() = startGame.call()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}