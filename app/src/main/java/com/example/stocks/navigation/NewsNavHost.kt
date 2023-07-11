package com.example.stocks.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import com.example.stocks.auth.navigation.authPageNavigationRoute
import com.example.stocks.auth.navigation.authScreen
import com.example.stocks.stocks_main_screen.navigation.articleDetailedScreen
import com.example.stocks.stocks_main_screen.navigation.newsScreen

@Composable
fun NewsNavHost(
    navController: NavHostController,
    modifier: Modifier = Modifier,
    startDestination: String = authPageNavigationRoute,
) {
    NavHost(
        navController = navController,
        startDestination = startDestination,
        modifier = modifier,
    ) {
        authScreen(navController)
        newsScreen(navController)
        articleDetailedScreen(navController)
    }
}
