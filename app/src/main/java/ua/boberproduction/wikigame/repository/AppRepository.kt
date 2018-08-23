package ua.boberproduction.wikigame.repository

import io.reactivex.Single
import ua.boberproduction.wikigame.models.Result
import ua.boberproduction.wikigame.repository.local.GameDataBase
import ua.boberproduction.wikigame.repository.network.WikiApi

class AppRepository(private val wikiApi: WikiApi,
                    private val gameDataBase: GameDataBase) : Repository {

    override fun saveResult(result: Result) {
        gameDataBase.resultsDao().insert(ua.boberproduction.wikigame.repository.local.Result(result))
    }

    override fun getArticleHtml(articleName: String, locale: String): Single<Resource<String>> {
        return wikiApi.getArticleHtml(articleName)
                .flatMap { response ->
                    val text = response.getText()
                    Single.just(Resource.Success(text))
                }
    }

    override fun getPhrases(level: Int): Single<Pair<String, String>> {
        return Single.just("Barcelona" to "Spain")
    }

}
