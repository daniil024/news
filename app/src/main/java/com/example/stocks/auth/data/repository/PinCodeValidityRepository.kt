package com.example.stocks.auth.data.repository

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow

class PinCodeValidityRepository {

    private val _pinCodeValidityStatusFlow: MutableStateFlow<PinCodeValidityStatus> =
        MutableStateFlow(PinCodeValidityStatus.NOT_VERIFIED)

    val pinCodeValidityStatusFlow: StateFlow<PinCodeValidityStatus>
        get() = _pinCodeValidityStatusFlow

    val validityStatus: PinCodeValidityStatus get() = _pinCodeValidityStatusFlow.value

    internal fun updatePinCodeValidityStatus(pinCodeValidityStatus: PinCodeValidityStatus) {
        _pinCodeValidityStatusFlow.value = pinCodeValidityStatus
    }
}

enum class PinCodeValidityStatus {
    PIN_CODE_IS_VALID, PIN_CODE_IS_INVALID, NOT_VERIFIED
}