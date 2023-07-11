package com.example.stocks.auth.navigation

import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.composable
import com.example.stocks.auth.ui.AuthRoute

const val authPageNavigationRoute = "auth_page_route"

fun NavController.navigateToAuthScreen(navOptions: NavOptions? = null) {
    this.navigate(authPageNavigationRoute, navOptions)
}

fun NavGraphBuilder.authScreen(navHostController: NavHostController) {
    composable(route = authPageNavigationRoute) {
        AuthRoute(navHostController = navHostController)
    }
}