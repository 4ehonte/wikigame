package ua.boberproduction.wikigame.mvvm.pregame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.repository.ResourceMapper
import ua.boberproduction.wikigame.repository.ResourcesRepository
import ua.boberproduction.wikigame.util.*
import java.util.*
import javax.inject.Inject

class PregameViewModel @Inject constructor(
        private val dataRepository: DataRepository,
        private val schedulerProvider: SchedulerProvider,
        private val preferencesRepository: PreferencesRepository,
        private val resourcesRepository: ResourcesRepository) : ViewModel() {

    private val disposables = CompositeDisposable()
    val errorMessage = SingleLiveEvent<String>()
    val phrases = SingleLiveEvent<Pair<String, String>>()
    val startGame = SingleLiveEvent<Unit>()
    val showInfoWindow by lazy { SingleLiveEvent<String>() }
    val summaryLoadingError by lazy { SingleLiveEvent<String>() }
    val phraseInfo by lazy { MutableLiveData<Pair<String, String>>() }

    fun onCreate() {
        loadPhrases()
    }

    /**
     * Load two phrases from the dataRepository (a phrase to begin game from, and a target phrase) into a LiveData object
     */
    private fun loadPhrases() {
        val levels = Levels(resourcesRepository)
        val userLevel = levels.getUserLevel(preferencesRepository.getTotalPoints()).levelNumber

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

    fun showInfoWindow(phrase: String) {
        showInfoWindow.postValue(phrase)
    }

    fun loadInfo(phrase: String) {
        val currentInfo = phraseInfo.value
        // if we already have info for the requested phrase loaded, just display it
        if (currentInfo?.first == phrase) {
            phraseInfo.postValue(currentInfo)
            // if not, load info
        } else {
            val locale = preferencesRepository.getAppLocale()
            dataRepository.getSummary(phrase, locale.toString())
                    .subscribeOn(schedulerProvider.io())
                    .observeOn(schedulerProvider.mainThread())
                    .map(ResourceMapper())
                    .subscribe({
                        phraseInfo.postValue(phrase to it)
                    }, {
                        summaryLoadingError.postValue(it.message)
                    })
                    .addToComposite(disposables)

        }
    }

    fun startButtonClicked() = startGame.call()

    override fun onCleared() {
        disposables.dispose()
        super.onCleared()
    }
}