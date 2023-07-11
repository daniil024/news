package com.example.stocks.auth.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.stocks.auth.data.repository.PIN_CODE_LENGTH
import com.example.stocks.ui.design_system.HintText
import com.example.stocks.ui.design_system.LightGrayColor
import com.example.stocks.ui.design_system.NewsTextStyle
import com.example.stocks.ui.design_system.NewsTextStyle.ConfirmationCodeBlockTextPlaceholder
import com.example.stocks.ui.design_system.NewsTextStyle.Hint14spDim
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf
import com.example.stocks.R as coreR


@Composable
fun AuthRoute(
    navHostController: NavHostController,
    authViewModel: AuthViewModel = koinViewModel { parametersOf(navHostController) }
) {
    AuthScreen(authViewModel)
}

@Composable
internal fun AuthScreen(viewModel: AuthViewModel) {
    val authUiState by viewModel.authUiState.collectAsStateWithLifecycle()

    Column {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(24.dp, 36.dp, 24.dp, 12.dp)
        ) {
            Column(modifier = Modifier.align(Alignment.TopStart)) {
                Text(
                    modifier = Modifier.padding(4.dp, 0.dp, 0.dp, 24.dp),
                    text = stringResource(id = coreR.string.auth_header),
                    style = NewsTextStyle.ScreenHeader1
                )

                Text(
                    modifier = Modifier
                        .padding(4.dp, 24.dp, 0.dp, 48.dp)
                        .fillMaxWidth(),
                    textAlign = TextAlign.Center,
                    text = authUiState.hint.ifBlank { stringResource(id = coreR.string.pin_code_clarification) },
                    style = NewsTextStyle.TextClarification
                )

                PinCodeBlocks(
                    modifier = Modifier.align(Alignment.CenterHorizontally),
                    viewModel = viewModel
                )
                ErrorTextHint(viewModel)
            }

            Box(modifier = Modifier.align(Alignment.BottomCenter)) {
                AuthorisationRestrictedHint(viewModel)
            }
        }
    }
}

@Composable
internal fun AuthorisationRestrictedHint(viewModel: AuthViewModel) {
    val authUiState by viewModel.authUiState.collectAsStateWithLifecycle()

    if (authUiState.authRestriction.isAuthRestricted) {
        Text(
            text = stringResource(
                id = coreR.string.auth_restriction_clarification,
                formatArgs = arrayOf(authUiState.authRestriction.timeUntilRestricted)
            ),
            style = Hint14spDim
        )
    }
}

@Composable
internal fun PinCodeBlocks(
    modifier: Modifier = Modifier,
    viewModel: AuthViewModel
) {
    val focusRequester = remember { FocusRequester() }
    val authUiState by viewModel.authUiState.collectAsStateWithLifecycle()

    BasicTextField(
        value = authUiState.pinCode.value,
        onValueChange = {
            if (authUiState.pinCode.value.text.length <= PIN_CODE_LENGTH) {
                val pinCodeTextFieldValue = TextFieldValue(
                    text = it.text,
                    selection = TextRange(it.text.length)
                )
                viewModel.updatePinInput(pinCodeTextFieldValue)
            }
        },
        modifier = modifier.focusRequester(focusRequester),
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.NumberPassword,
            imeAction = ImeAction.Done
        ),
        decorationBox = {
            Row(horizontalArrangement = Arrangement.Start) {
                repeat(PIN_CODE_LENGTH) { index ->
                    val char = when {
                        index >= authUiState.pinCode.value.text.length -> ""
                        else -> authUiState.pinCode.value.text[index].toString()
                    }

                    if (authUiState.pinCode.value.text.isEmpty()) {
                        PinCodeBlock("0", ConfirmationCodeBlockTextPlaceholder, LightGrayColor)
                    } else {
                        PinCodeBlock(
                            char,
                            authUiState.pinCode.pinCodeBlocksStyle.textStyle,
                            authUiState.pinCode.pinCodeBlocksStyle.blockColor
                        )
                    }
                    Spacer(modifier = Modifier.width(8.dp))
                }
            }

        }
    )

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
    }
}

@Composable
internal fun PinCodeBlock(char: String, textStyle: TextStyle, backgroundColor: Color) {
    Text(
        modifier = Modifier
            .size(50.dp, 60.dp)
            .background(backgroundColor, RoundedCornerShape(8.dp))
            .padding(2.dp),
        text = char,
        style = textStyle,
        textAlign = TextAlign.Center
    )
}

@Composable
internal fun ErrorTextHint(viewModel: AuthViewModel) {
    val authUiState by viewModel.authUiState.collectAsStateWithLifecycle()

    if (authUiState.errorHint.isVisible) {
        HintText(
            modifier = Modifier.padding(start = 4.dp, top = 16.dp),
            textValue = authUiState.errorHint.text,
            textStyle = authUiState.errorHint.textStyle
        )
    }
}