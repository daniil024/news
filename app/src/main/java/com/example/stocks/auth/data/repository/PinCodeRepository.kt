package com.example.stocks.auth.data.repository

import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.TextFieldValue
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

internal const val PIN_CODE_LENGTH = 4
private val INITIAL_PIN_CODE_TEXT_FIELD_VALUE = TextFieldValue(text = "", selection = TextRange(0))

class PinCodeRepository {

    private val _pinCodeStateFlow: MutableStateFlow<TextFieldValue> =
        MutableStateFlow(INITIAL_PIN_CODE_TEXT_FIELD_VALUE)

    internal val pinCodeStateFlow: StateFlow<TextFieldValue> get() = _pinCodeStateFlow

    internal fun updateEnteredPinCode(pinCodeTextFieldValue: TextFieldValue) {
        _pinCodeStateFlow.value = pinCodeTextFieldValue
    }
}