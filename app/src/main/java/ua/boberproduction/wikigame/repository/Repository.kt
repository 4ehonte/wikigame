package ua.boberproduction.wikigame.repository

import io.reactivex.Single
import ua.boberproduction.wikigame.models.Result
import java.util.*

interface DataRepository {
    fun getPhrases(level: Int): Single<List<String>>
    fun getArticleHtml(articleName: String, locale: String): Single<Resource<String>>
    fun getSummary(articleName: String, locale: String): Single<Resource<String>>
    fun saveResult(result: Result)
    fun clearResults()
    fun getResults(): Single<List<Result>>
    fun getTotalClicks(): Single<Int>
    fun getTotalTime(): Single<Int>
}

interface PreferencesRepository {
    fun getAppLocale(): Locale
    fun setTotalPoints(points: Int)
    fun getTotalPoints(): Int
    fun setSoundEnabled(isEnabled: Boolean)
    fun getSoundEnabled(): Boolean
    fun setFontSize(fontSize: Int)
    fun getFontSize(): Int
}

interface ResourcesRepository {
    fun getString(res: Int): String
    fun getLevelPoints(): IntArray
    fun getRankIcons(): IntArray
    fun getRankNames(): Array<String>
}

