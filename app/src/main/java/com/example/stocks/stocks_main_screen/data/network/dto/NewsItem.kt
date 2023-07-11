package com.example.stocks.stocks_main_screen.data.network.dto

import java.util.Date

data class NewsItem(
    val title: String,
    val imageUrl: String,
    val category: Category,
    val publishDate: Date,
    val previewText: String,
    val fullText: String
)

data class Category(
    val id: Int,
    val name: String
)
