package com.example.stocks.stocks_main_screen.data.repository

import com.example.stocks.stocks_main_screen.data.network.dto.Article
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class OpenedArticleRepository {

    private val _openedArticleStateFlow: MutableStateFlow<Article> = MutableStateFlow(Article())
    internal val openedArticleStateFlow = _openedArticleStateFlow.asStateFlow()

    internal fun updateOpenedArticle(article: Article) {
        _openedArticleStateFlow.update { article }
    }
}