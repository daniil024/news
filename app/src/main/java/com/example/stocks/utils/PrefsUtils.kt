package com.example.stocks.utils

import android.content.Context

internal const val USER_DATA_PREFS_NAME = "com.culturalexchange.traveler.user_token"

internal const val TIME_UNTIL_AUTH_RESTRICTED = "time_until_auth_restricted"
internal const val USER_LAST_ENTERED_TIME = "user_last_entered_time"
internal const val USER_PIN_ENTER_WRONG = "user_pin_enter_wrong"
internal const val USER_PIN = "user_pin"

class PrefsUtils(context: Context) {

    private val prefs = context.getSharedPreferences(USER_DATA_PREFS_NAME, Context.MODE_PRIVATE)

    fun saveLastEnteredTime(time: Long) {
        with(prefs.edit()) {
            putLong(USER_LAST_ENTERED_TIME, time)
            apply()
        }
    }

    fun getLastEnteredTime(): Long {
        return prefs.getLong(USER_LAST_ENTERED_TIME, -1)
    }

    fun savePin(pin: String) {
        with(prefs.edit()) {
            putString(USER_PIN, pin)
            apply()
        }
    }

    fun getPin(): String? {
        return prefs.getString(USER_PIN, "")
    }

    fun setNewWrongPinEnter() {
        val wrongEnteringCount = prefs.getInt(USER_PIN_ENTER_WRONG, 0) + 1
        with(prefs.edit()) {
            putInt(USER_PIN_ENTER_WRONG, wrongEnteringCount)
            apply()
        }
    }

    fun getWrongPinEnterCount(): Int {
        return prefs.getInt(USER_PIN_ENTER_WRONG, 0)
    }

    fun resetWrongPinEnter() {
        with(prefs.edit()) {
            putInt(USER_PIN_ENTER_WRONG, 0)
            apply()
        }
    }

    fun saveTimeUntilAuthRestricted(time: Long) {
        with(prefs.edit()) {
            putLong(TIME_UNTIL_AUTH_RESTRICTED, time)
            apply()
        }
    }

    fun getTimeUntilAuthRestricted(): Long {
        return prefs.getLong(TIME_UNTIL_AUTH_RESTRICTED, -1)
    }
}