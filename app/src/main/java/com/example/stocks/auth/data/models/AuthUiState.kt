package com.example.stocks.auth.data.models

import androidx.compose.ui.text.TextStyle

data class AuthUiState(
    val hint: String,
    val pinCode: PinCode,
    val errorHint: ErrorHint,
    val authRestriction: AuthRestriction,
) {

    companion object {
        val EMPTY = AuthUiState("", PinCode.EMPTY, ErrorHint.EMPTY, AuthRestriction.EMPTY)
    }
}

data class ErrorHint(
    val text: String,
    val textStyle: TextStyle,
    val isVisible: Boolean
) {

    companion object {
        val EMPTY = ErrorHint("", TextStyle(), false)
    }
}
