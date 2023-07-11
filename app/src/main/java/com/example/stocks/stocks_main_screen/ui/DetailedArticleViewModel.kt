package com.example.stocks.stocks_main_screen.ui

import androidx.lifecycle.ViewModel
import com.example.stocks.stocks_main_screen.data.network.dto.Article
import com.example.stocks.stocks_main_screen.data.repository.OpenedArticleRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class DetailedArticleViewModel(
    private val openedArticleRepository: OpenedArticleRepository
) : ViewModel() {

    internal val openedArticle: Article
        get() = openedArticleRepository.openedArticleStateFlow.value
}

fun Date.makeArticlePublishDate(): String {
    return SimpleDateFormat("dd/MM/yyyy hh:mm", Locale.US).format(this)
}