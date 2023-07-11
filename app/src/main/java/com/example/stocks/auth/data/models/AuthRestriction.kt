package com.example.stocks.auth.data.models

data class AuthRestriction(
    val isAuthRestricted: Boolean,
    val timeUntilRestricted: String
) {

    companion object {
        val EMPTY = AuthRestriction(false, "")
    }
}