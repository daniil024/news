package com.example.stocks.utils.di

import com.example.stocks.utils.PrefsUtils
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

val prefsModule = module {

    singleOf(::PrefsUtils)
}