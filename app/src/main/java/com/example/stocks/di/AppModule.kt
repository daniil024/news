package com.example.stocks.di

import com.example.stocks.auth.data.repository.PinCodeRepository
import com.example.stocks.auth.data.repository.PinCodeValidityRepository
import com.example.stocks.auth.domain.CheckIfAuthIsRestrictedInteractor
import com.example.stocks.auth.domain.IsUserAuthorisedInteractor
import com.example.stocks.auth.ui.AuthViewModel
import com.example.stocks.stocks_main_screen.data.repository.OpenedArticleRepository
import com.example.stocks.stocks_main_screen.data.repository.TopStoriesResponseRepository
import com.example.stocks.stocks_main_screen.ui.DetailedArticleViewModel
import com.example.stocks.stocks_main_screen.ui.TopStoriesViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val appModule = module {

    factoryOf(::IsUserAuthorisedInteractor)
    factoryOf(::CheckIfAuthIsRestrictedInteractor)

    singleOf(::TopStoriesResponseRepository)
    singleOf(::PinCodeValidityRepository)
    singleOf(::OpenedArticleRepository)
    singleOf(::PinCodeRepository)

    viewModel { params ->
        AuthViewModel(
            get(),
            get(),
            get(),
            get(),
            get(),
            navHostController = params.get()
        )
    }

    viewModel { params ->
        TopStoriesViewModel(get(), get(), get(), params.get())
    }
    viewModelOf(::DetailedArticleViewModel)
}
