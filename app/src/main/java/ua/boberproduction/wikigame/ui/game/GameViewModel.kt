package ua.boberproduction.wikigame.ui.game

import androidx.lifecycle.ViewModel
import ua.boberproduction.wikigame.repository.PreferencesProvider
import ua.boberproduction.wikigame.repository.Repository
import ua.boberproduction.wikigame.util.SchedulerProvider
import ua.boberproduction.wikigame.util.SingleLiveEvent
import javax.inject.Inject

class GameViewModel @Inject constructor(
        repository: Repository,
        schedulerProvider: SchedulerProvider,
        preferencesProvider: PreferencesProvider) : ViewModel() {
    val html = SingleLiveEvent<String>()

    fun onCreate(phrases: Pair<String, String>) {
    }
}