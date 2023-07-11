package com.example.stocks.stocks_main_screen.data.network.dto

import com.google.gson.annotations.SerializedName
import java.util.Date

data class TopStoriesResponse(

    @SerializedName("status")
    val status: String = "",

    @SerializedName("results")
    val news: List<Article> = emptyList(),
) {

    companion object {
        val EMPTY = TopStoriesResponse()
    }
}

data class Article(

    @SerializedName("section")
    val section: String = "",

    @SerializedName("subsection")
    val subsection: String = "",

    @SerializedName("title")
    val title: String = "",

    @SerializedName("abstract")
    val abstractDescription: String = "",

    @SerializedName("published_date")
    val publishDate: Date = Date(),

    @SerializedName("multimedia")
    val multimedia: List<Multimedia>? = emptyList(),

    @SerializedName("url")
    val url: String = "",
)

data class Multimedia(

    @SerializedName("url")
    val url: String = "",

    @SerializedName("type")
    val type: String = "",
)
