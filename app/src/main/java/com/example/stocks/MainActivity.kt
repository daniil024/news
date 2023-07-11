package com.example.stocks

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.stocks.auth.domain.IsUserAuthorisedInteractor
import com.example.stocks.auth.navigation.navigateToAuthScreen
import com.example.stocks.navigation.NewsNavHost
import com.example.stocks.stocks_main_screen.navigation.navigateToNewsScreen
import com.example.stocks.ui.theme.StocksTheme
import org.koin.android.ext.android.inject

class MainActivity : ComponentActivity() {

    private val isUserAuthorisedInteractor: IsUserAuthorisedInteractor by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            val isUserAuthorised = isUserAuthorisedInteractor.isUserAuthorised()

            StocksTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    StocksAppRoute(isUserAuthorised = isUserAuthorised)
                }
            }
        }
    }
}

@Composable
fun StocksAppRoute(
    navHostController: NavHostController = rememberNavController(),
    isUserAuthorised: Boolean
) {
    NewsNavHost(
        navController = navHostController
    )
    if (isUserAuthorised) {
        navHostController.navigateToNewsScreen()
    } else {
        navHostController.navigateToAuthScreen()
    }
}