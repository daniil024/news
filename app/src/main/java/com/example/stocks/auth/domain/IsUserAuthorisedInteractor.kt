package com.example.stocks.auth.domain

import android.annotation.SuppressLint
import com.example.stocks.utils.PrefsUtils
import java.time.Instant
import java.time.LocalDateTime
import java.util.TimeZone

internal const val USER_LAST_ENTERED_TIME_DEFAULT_VALUE: Long = -1
internal const val MAX_VALID_TIME_WITHOUT_ACTION_IN_MS: Long = 60000

class IsUserAuthorisedInteractor(
    private val prefsUtils: PrefsUtils
) {

    @SuppressLint("NewApi")
    fun isUserAuthorised(): Boolean {
        val lastEnteredTime = prefsUtils.getLastEnteredTime()
        if (lastEnteredTime == USER_LAST_ENTERED_TIME_DEFAULT_VALUE) {
            return false
        }

        val lastEnteredTimeInMillisWithOffset =
            lastEnteredTime + MAX_VALID_TIME_WITHOUT_ACTION_IN_MS

        val currentTime = System.currentTimeMillis()
        return currentTime < lastEnteredTimeInMillisWithOffset
    }
}