package ua.boberproduction.wikigame.mvvm.menu

import androidx.lifecycle.ViewModel
import ua.boberproduction.wikigame.util.SingleLiveEvent
import javax.inject.Inject

class MainMenuViewModel @Inject constructor() : ViewModel() {

    val showPregame = SingleLiveEvent<Unit>()
    val showStatistics = SingleLiveEvent<Unit>()
    val showSettings = SingleLiveEvent<Unit>()
    val showPlayStore = SingleLiveEvent<Unit>()

    fun playBtnClicked() = showPregame.call()
    fun statisticsBtnClicked() = showStatistics.call()
    fun settingsBtnClicked() = showSettings.call()
    fun rateBtnClicked() = showPlayStore.call()
}