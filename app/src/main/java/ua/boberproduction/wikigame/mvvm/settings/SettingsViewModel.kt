package ua.boberproduction.wikigame.mvvm.settings

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.repository.DataRepository
import ua.boberproduction.wikigame.repository.PreferencesRepository
import ua.boberproduction.wikigame.repository.ResourcesRepository
import ua.boberproduction.wikigame.util.SingleLiveEvent
import javax.inject.Inject

class SettingsViewModel @Inject constructor(
        private val dataRepository: DataRepository,
        private val preferencesRepository: PreferencesRepository,
        private val resourcesRepository: ResourcesRepository) : ViewModel() {

    val fontZoomPref = MutableLiveData<Int>()
    val enableSounds = MutableLiveData<Boolean>()
    val resetScoreAlertDialog = SingleLiveEvent<Unit>()
    val message by lazy { SingleLiveEvent<String>() }

    fun loadCurrentPrefs() {
        fontZoomPref.value = preferencesRepository.getFontZoom()
        enableSounds.value = preferencesRepository.getSoundEnabled()
    }

    fun onFontSizeToggled(isLarge: Boolean) {
        val fontSize = if (isLarge) 130 else 100
        preferencesRepository.setFontSize(fontSize)
    }

    fun onMuteToggled(isSoundEnabled: Boolean) {
        preferencesRepository.setSoundEnabled(isSoundEnabled)
    }

    fun resetScoreClicked() = resetScoreAlertDialog.call()

    fun resetScores() {
        preferencesRepository.setTotalPoints(0)
        dataRepository.clearResults()

        message.postValue(resourcesRepository.getString(R.string.score_reset_successful))
    }
}