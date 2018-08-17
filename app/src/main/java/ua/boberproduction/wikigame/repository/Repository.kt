package ua.boberproduction.wikigame.repository

import io.reactivex.Single

interface Repository {
    fun getPhrases(level: Int): Single<Pair<String, String>>
}