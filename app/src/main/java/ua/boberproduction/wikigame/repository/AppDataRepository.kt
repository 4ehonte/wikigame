package ua.boberproduction.wikigame.repository

import android.app.Application
import io.reactivex.Single
import ua.boberproduction.wikigame.R
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.repository.local.GameDataBase
import ua.boberproduction.wikigame.repository.network.WikiApi

class AppDataRepository(private val wikiApi: WikiApi,
                        private val gameDataBase: GameDataBase,
                        private val application: Application) : DataRepository {

    override fun saveResult(result: Result) {
        gameDataBase.resultsDao().insert(ua.boberproduction.wikigame.repository.local.Result.fromModel(result))
    }


    override fun getArticleHtml(articleName: String, locale: String): Single<Resource<String>> {
        return wikiApi.getArticleHtml(articleName)
                .flatMap { response ->
                    val text = response.getText()
                    Single.just(Resource.Success(text))
                }
    }

    override fun getPhrases(level: Int): Single<List<String>> {
        val phrasesArrayRes = when (level) {
            in 1..3 -> R.array.phrases_level_1_to_3
            in 2..10 -> R.array.phrases_level_4_to_10
            in 11..20 -> R.array.phrases_level_11_to_20
            in 21..30 -> R.array.phrases_level_21_to_30
            else -> R.array.phrases_level_21_to_30
        }
        val phrasesArray = application.resources.getStringArray(phrasesArrayRes)
        return Single.just(phrasesArray.asList())
    }

    override fun getSummary(articleName: String, locale: String): Single<Resource<String>> {
        return wikiApi.getSummary(locale, articleName)
                .flatMap { response ->
                    val summary = response.getFirstPage()?.content
                    if (summary.isNullOrEmpty()) {
                        Single.just(Resource.Failure<String>())
                    } else Single.just(Resource.Success(summary))
                }
    }

    override fun getResults(): Single<List<Result>> {
        return gameDataBase.resultsDao().getAll()
                .toObservable()
                .flatMapIterable { it }
                .map { dbResult -> dbResult.mapToModel() }
                .toList()
    }

    override fun getTotalClicks(): Single<Int> = gameDataBase.resultsDao().getTotalClicks()

    override fun getTotalTime(): Single<Int> = gameDataBase.resultsDao().getTotalTime()
}
