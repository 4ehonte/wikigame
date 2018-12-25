package ua.boberproduction.wikigame.mvvm.menu

import androidx.lifecycle.ViewModel
import ua.boberproduction.wikigame.util.SchedulerProvider
import ua.boberproduction.wikigame.util.SingleLiveEvent
import javax.inject.Inject

class MainMenuViewModel @Inject constructor() : ViewModel() {

    val playBtnClick = SingleLiveEvent<Unit>()

    fun playBtnClicked() = playBtnClick.call()

}