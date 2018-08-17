package ua.boberproduction.wikigame.repository

import io.reactivex.Single
import ua.boberproduction.wikigame.repository.network.WikiApi

class AppRepository(private val wikiApi: WikiApi) : Repository {

    override fun getPhrases(level: Int): Single<Pair<String, String>> {
        return Single.just("One" to "Two")
    }

}
