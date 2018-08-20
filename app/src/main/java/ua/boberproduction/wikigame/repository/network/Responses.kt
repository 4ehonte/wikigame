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