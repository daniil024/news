package com.example.stocks.stocks_main_screen.data.network.api

import okhttp3.HttpUrl
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import java.io.IOException

class ApiKeyInterceptor : Interceptor {

    @Throws(IOException::class)
    override fun intercept(chain: Interceptor.Chain): Response {
        val requestWithoutApiKey: Request = chain.request()
        val url: HttpUrl = requestWithoutApiKey.url
            .newBuilder()
            .addQueryParameter(API_KEY_HEADER_NAME, TOP_STORIES_API_KEY)
            .build()
        val requestWithApiKey: Request = requestWithoutApiKey.newBuilder()
            .url(url)
            .addHeader(API_KEY_HEADER_NAME, TOP_STORIES_API_KEY)
            .build()
        return chain.proceed(requestWithApiKey)
    }

    companion object {
        private const val TOP_STORIES_API_KEY = "lldO8stNovdJNVUx1kWUKGWbLkBttnyC"
        private const val API_KEY_HEADER_NAME = "api-key"
    }
}