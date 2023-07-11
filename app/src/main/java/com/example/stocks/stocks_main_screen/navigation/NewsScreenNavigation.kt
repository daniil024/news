package com.example.stocks.stocks_main_screen.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.stocks.stocks_main_screen.ui.ArticleDetailedRoute
import com.example.stocks.stocks_main_screen.ui.ArticlesRoute

const val newsPageNavigationRoute = "news_page_route"

fun NavController.navigateToNewsScreen(navOptions: NavOptions? = null) {
    popBackStack()
    this.navigate(newsPageNavigationRoute, navOptions)
}

fun NavGraphBuilder.newsScreen(navHostController: NavHostController) {
    composable(route = newsPageNavigationRoute) {
        ArticlesRoute(navHostController = navHostController)
    }
}

const val articleDetailedPageNavigationRoute = "article_detailed_page_route"

fun NavController.navigateToArticleDetailedScreen(navOptions: NavOptions? = null) {
    this.navigate(articleDetailedPageNavigationRoute, navOptions)
}

fun NavGraphBuilder.articleDetailedScreen(navHostController: NavHostController) {
    composable(route = articleDetailedPageNavigationRoute) {
        ArticleDetailedRoute(navHostController = navHostController)
    }
}