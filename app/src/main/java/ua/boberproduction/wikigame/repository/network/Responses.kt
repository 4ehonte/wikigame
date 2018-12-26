package ua.boberproduction.wikigame.repository.network

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ArticleResponse(
        @Json(name = "parse")
        val parseResponse: ParseResponse) {
    fun getText() = parseResponse.text.text
}

@JsonClass(generateAdapter = true)
class ParseResponse(
        @Json(name = "title")
        val title: String,
        @Json(name = "pageid")
        val pageid: Int,
        @Json(name = "text")
        val text: TextContent)


@JsonClass(generateAdapter = true)
class TextContent(
        @Json(name = "*")
        val text: String)

@JsonClass(generateAdapter = true)
class SummaryResponse(
        @Json(name = "query")
        val query: Query) {

    fun getFirstPage(): Page? {
        return query.pages?.entries?.iterator()?.next()?.value
    }
}

@JsonClass(generateAdapter = true)
class Query(
        @Json(name = "pages")
        var pages: Map<String, Page>? = null)

@JsonClass(generateAdapter = true)
class Page(
        @Json(name = "pageid")
        val id: Long = 0,
        @Json(name = "title")
        val title: String? = null,
        @Json(name = "extract")
        val content: String? = null)