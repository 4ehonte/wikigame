package ua.boberproduction.wikigame.ui.pregame

import androidx.lifecycle.ViewModel
import ua.boberproduction.wikigame.network.WikiApi
import ua.boberproduction.wikigame.util.SchedulerProvider
import javax.inject.Inject

class PregameViewModel @Inject constructor(
        private val schedulerProvider: SchedulerProvider) : ViewModel() {

    @Inject
    lateinit var wikiApi: WikiApi

}