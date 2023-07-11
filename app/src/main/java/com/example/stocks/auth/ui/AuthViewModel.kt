package com.example.stocks.auth.ui

import android.annotation.SuppressLint
import android.app.Application
import android.util.Log
import androidx.compose.ui.text.input.TextFieldValue
import androidx.lifecycle.AndroidViewModel
import androidx.navigation.NavHostController
import com.example.stocks.auth.data.models.AuthUiState
import com.example.stocks.auth.data.models.ErrorHint
import com.example.stocks.auth.data.repository.PIN_CODE_LENGTH
import com.example.stocks.auth.data.repository.PinCodeRepository
import com.example.stocks.auth.data.repository.PinCodeValidityRepository
import com.example.stocks.auth.domain.CheckIfAuthIsRestrictedInteractor
import com.example.stocks.stocks_main_screen.navigation.navigateToNewsScreen
import com.example.stocks.ui.design_system.NewsTextStyle
import com.example.stocks.utils.PrefsUtils
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import com.example.stocks.R as coreR

class AuthViewModel(
    private val app: Application,
    private val pinCodeValidityRepository: PinCodeValidityRepository,
    private val pinCodeRepository: PinCodeRepository,
    private val prefsUtils: PrefsUtils,
    private val checkIfAuthIsRestrictedInteractor: CheckIfAuthIsRestrictedInteractor,
    private val navHostController: NavHostController
) : AndroidViewModel(app) {

    private val ioScope: CoroutineScope = CoroutineScope(SupervisorJob() + Dispatchers.IO)

    private val _authUiState: MutableStateFlow<AuthUiState> = MutableStateFlow(AuthUiState.EMPTY)
    internal val authUiState: StateFlow<AuthUiState>
        get() = _authUiState

    init {
        handleRestriction()
        handleHintClarification()
        launchPinCodeUpdatesListener()
    }

    @SuppressLint("NewApi")
    private fun handleRestriction() {
        if (checkIfAuthIsRestrictedInteractor.isRestricted()) {
            val time = Date(prefsUtils.getTimeUntilAuthRestricted())
            val format = SimpleDateFormat("HH:mm", Locale.US)

            val authUiState = authUiState.value
            _authUiState.update {
                authUiState.copy(
                    authRestriction = authUiState.authRestriction.copy(
                        isAuthRestricted = true,
                        timeUntilRestricted = format.format(time)
                    )
                )
            }
        }
        handleAuthRestrictments()
    }

    private fun handleHintClarification() {
        val isPinCodeSet = !prefsUtils.getPin().isNullOrBlank()
        var hint = app.getString(coreR.string.pin_code_clarification)
        if (!isPinCodeSet) {
            hint = app.getString(coreR.string.pin_code_clarification_on_first_enter)
        }

        val authUiState = authUiState.value
        _authUiState.update {
            authUiState.copy(hint = hint)
        }
    }

    private fun launchPinCodeUpdatesListener() {
        ioScope.launch {
            pinCodeRepository.pinCodeStateFlow.collect { newPin ->
                val authUiState = authUiState.value
                _authUiState.update { authUiState.copy(pinCode = authUiState.pinCode.copy(value = newPin)) }
            }
        }
    }

    fun updatePinInput(pinCodeTextFieldValue: TextFieldValue) {
        val isAuthRestricted = checkIfAuthIsRestrictedInteractor.isRestricted()

        if (isAuthRestricted.not() && authUiState.value.authRestriction.isAuthRestricted) {
            val authUiState = authUiState.value
            _authUiState.update {
                authUiState.copy(
                    authRestriction = authUiState.authRestriction.copy(
                        isAuthRestricted = false,
                        timeUntilRestricted = ""
                    )
                )
            }

            prefsUtils.saveTimeUntilAuthRestricted(-1)
            prefsUtils.resetWrongPinEnter()
        }

        if (pinCodeTextFieldValue.text.length > PIN_CODE_LENGTH || isAuthRestricted) {
            return
        }

        pinCodeRepository.updateEnteredPinCode(pinCodeTextFieldValue)
        if (pinCodeTextFieldValue.text.length == PIN_CODE_LENGTH) {
            val savedPin = prefsUtils.getPin()
            if (savedPin.isNullOrBlank()) {
                handleFirstEnter(pinCodeTextFieldValue.text)
                return
            }

            handlePin(savedPin = savedPin, pinCode = pinCodeTextFieldValue.text)
        }
    }

    private fun handleFirstEnter(newPin: String) {
        prefsUtils.savePin(newPin)
        prefsUtils.saveLastEnteredTime(System.currentTimeMillis())
        navigateToNewsScreen()
    }

    private fun handlePin(savedPin: String, pinCode: String) {
        if (savedPin == pinCode) {
            navigateToNewsScreen()
            prefsUtils.resetWrongPinEnter()
            prefsUtils.saveLastEnteredTime(System.currentTimeMillis())
//            pinCodeRepository.updateEnteredPinCode(TextFieldValue())
        } else {
            prefsUtils.setNewWrongPinEnter()
            handleAuthRestrictments()

            pinCodeRepository.updateEnteredPinCode(TextFieldValue())
            val authUiState = authUiState.value
            _authUiState.update {
                authUiState.copy(
                    errorHint = ErrorHint(
                        "Invalid PIN-code",
                        textStyle = NewsTextStyle.ErrorHint14sp,
                        isVisible = true
                    )
                )
            }
        }
    }

    private fun handleAuthRestrictments() {
        val countOfWrongEntering = prefsUtils.getWrongPinEnterCount()
        if (countOfWrongEntering >= 5) {
            val isAuthRestricted = checkIfAuthIsRestrictedInteractor.isRestricted()
            var timeUntilRestricted: Long = prefsUtils.getTimeUntilAuthRestricted()
            if (isAuthRestricted.not()) {
                timeUntilRestricted = System.currentTimeMillis() + 60000
                prefsUtils.saveTimeUntilAuthRestricted(timeUntilRestricted)
            }
            prefsUtils.resetWrongPinEnter()

            val time = Date(timeUntilRestricted)
            val format = SimpleDateFormat("HH:mm", Locale.US)

            val authUiState = authUiState.value
            _authUiState.update {
                authUiState.copy(
                    authRestriction = authUiState.authRestriction.copy(
                        isAuthRestricted = true,
                        timeUntilRestricted = format.format(time)
                    )
                )
            }
        }
    }

    private fun navigateToNewsScreen() {
        navHostController.popBackStack()
        navHostController.navigateToNewsScreen()
    }
}