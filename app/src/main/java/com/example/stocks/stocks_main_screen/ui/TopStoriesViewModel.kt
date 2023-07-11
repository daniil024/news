package com.example.stocks.stocks_main_screen.ui

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import com.example.stocks.stocks_main_screen.data.network.api.BaseNetworkService
import com.example.stocks.stocks_main_screen.data.network.api.TopStoriesApiService
import com.example.stocks.stocks_main_screen.data.network.dto.Article
import com.example.stocks.stocks_main_screen.data.network.dto.TopStoriesResponse
import com.example.stocks.stocks_main_screen.data.network.models.NewsCategory
import com.example.stocks.stocks_main_screen.data.repository.OpenedArticleRepository
import com.example.stocks.stocks_main_screen.data.repository.TopStoriesResponseRepository
import com.example.stocks.stocks_main_screen.navigation.navigateToArticleDetailedScreen
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import retrofit2.Response
import java.net.UnknownHostException

class TopStoriesViewModel(
    app: Application,
    private val topStoriesResponseRepository: TopStoriesResponseRepository,
    private val openedArticleRepository: OpenedArticleRepository,
    private val navHostController: NavHostController,
) : AndroidViewModel(app) {

    private val exceptionHandler: CoroutineExceptionHandler = CoroutineExceptionHandler { _, _ -> }
    private val ioScope: CoroutineScope =
        CoroutineScope(SupervisorJob() + Dispatchers.IO + exceptionHandler)

    internal val articlesFlow: StateFlow<TopStoriesResponse>
        get() = topStoriesResponseRepository.topStoriesResponseFlow

    private val _isLoading: MutableStateFlow<Boolean> = MutableStateFlow(false)
    internal var isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _responseErrorStateFlow: MutableStateFlow<String> = MutableStateFlow("")
    internal val responseErrorStateFlow = _responseErrorStateFlow.asStateFlow()

    init {
        updateTopStories()
    }

    private fun updateTopStories() {
        ioScope.launch {
            val topStoriesService =
                BaseNetworkService.createService(TopStoriesApiService::class.java)
            try {
                val topStories =
                    topStoriesService.getTopStories(NewsCategory.values().random().serverValue)
                handleTopStoriesResponse(topStories)
            } catch (e: UnknownHostException) {
                _responseErrorStateFlow.update { "Check your network connection." }
            }
        }
    }

    private fun handleTopStoriesResponse(topStoriesResponse: Response<TopStoriesResponse>) {
        if (topStoriesResponse.isSuccessful) {
            _responseErrorStateFlow.update { "" }
            val topStories = topStoriesResponse.body() ?: TopStoriesResponse.EMPTY
            topStoriesResponseRepository.updateTopStoriesResponse(topStories)
        } else {
            handleNetworkErrors(topStoriesResponse)
        }
    }

    private fun handleNetworkErrors(topStoriesResponse: Response<TopStoriesResponse>) {
        when (topStoriesResponse.code()) {
            401 -> _responseErrorStateFlow.update { "Problem occurred with authorization." }
            403 -> _responseErrorStateFlow.update { "You don't have permission to news." }
            404 -> _responseErrorStateFlow.update { "There are no news." }
            429 -> _responseErrorStateFlow.update { "Do not update news so often." }
            else -> _responseErrorStateFlow.update { "Problem occurred while updating data." }
        }
    }

    internal fun swipedToRefresh() {
        ioScope.launch {
            _isLoading.update { true }
            delay(200)
            updateTopStories()
            delay(800)
            _isLoading.update { false }
        }
    }

    internal fun onArticleClicked(article: Article) {
        openedArticleRepository.updateOpenedArticle(article)
        navHostController.navigateToArticleDetailedScreen()
    }
}