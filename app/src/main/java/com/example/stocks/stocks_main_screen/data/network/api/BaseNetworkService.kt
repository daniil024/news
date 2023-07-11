package com.example.stocks.stocks_main_screen.data.network.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object BaseNetworkService {

    private const val BASE_URL = "https://api.nytimes.com/svc/"
    private const val TOP_STORIES_API_KEY = "lldO8stNovdJNVUx1kWUKGWbLkBttnyC"
    private const val API_KEY_HEADER_NAME = "api-key"

    private val HTTPLoggerInterceptor = HttpLoggingInterceptor().apply {
        this.level = HttpLoggingInterceptor.Level.BODY
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(ApiKeyInterceptor())
        .addInterceptor(HTTPLoggerInterceptor).build()

    // Gson implementation now is empty, but can be enriched later
    private val gson = GsonBuilder().create()

    private val retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    fun <T> createService(serviceClass: Class<T>): T {
        return retrofit.create(serviceClass)
    }
}