package com.example.stocks.auth.domain

import android.annotation.SuppressLint
import com.example.stocks.utils.PrefsUtils
import java.time.Instant
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.temporal.ChronoField
import java.util.TimeZone

class CheckIfAuthIsRestrictedInteractor(
    private val prefsUtils: PrefsUtils
) {

    @SuppressLint("NewApi")
    fun isRestricted(): Boolean {
        val timeUntilAuthRestricted = prefsUtils.getTimeUntilAuthRestricted()
        val now = System.currentTimeMillis()

        return now < timeUntilAuthRestricted
    }
}