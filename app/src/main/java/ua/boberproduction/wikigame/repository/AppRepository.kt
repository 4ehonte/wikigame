package ua.boberproduction.wikigame.repository

import io.reactivex.Single
import ua.boberproduction.wikigame.repository.network.WikiApi

class AppRepository(private val wikiApi: WikiApi) : Repository {

    override fun getArticleHtml(articleName: String, locale: String): Single<Resource<String>> {
        return wikiApi.getArticleHtml(articleName)
                .flatMap { response ->
                    val text = response.getText()
                    Single.just(Resource.Success(text))
                }
    }

    override fun getPhrases(level: Int): Single<Pair<String, String>> {
        return Single.just("Barcelona" to "Albert Einstein")
    }

}
