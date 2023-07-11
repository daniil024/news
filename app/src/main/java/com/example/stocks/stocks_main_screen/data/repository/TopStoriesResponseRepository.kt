package com.example.stocks.stocks_main_screen.data.repository

import com.example.stocks.stocks_main_screen.data.network.dto.TopStoriesResponse
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class TopStoriesResponseRepository {

    private val _topStoriesResponseFlow: MutableStateFlow<TopStoriesResponse> =
        MutableStateFlow(TopStoriesResponse())

    val topStoriesResponseFlow: StateFlow<TopStoriesResponse> get() = _topStoriesResponseFlow

    fun updateTopStoriesResponse(topStoriesResponse: TopStoriesResponse) {
        val topStories = topStoriesResponse.copy(
            news = topStoriesResponse.news.filter { it.section.isNotBlank() && it.url.isNotBlank() }
        )
        _topStoriesResponseFlow.update { topStories }
    }
}