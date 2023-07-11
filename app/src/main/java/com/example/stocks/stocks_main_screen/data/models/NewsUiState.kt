package com.example.stocks.stocks_main_screen.data.models

import com.example.stocks.stocks_main_screen.data.network.dto.Article
import kotlinx.coroutines.flow.Flow

data class NewsUiState(
    val news: Flow<List<Article>>
)
