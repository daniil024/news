package com.example.stocks.stocks_main_screen.data.network.api

import com.example.stocks.stocks_main_screen.data.network.dto.TopStoriesResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path

interface TopStoriesApiService {

    @GET("topstories/v2/{section}.json")
    suspend fun getTopStories(@Path("section") section: String): Response<TopStoriesResponse>
}