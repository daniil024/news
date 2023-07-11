package com.example.stocks.auth.data.models

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.TextFieldValue
import com.example.stocks.ui.design_system.ErrorBackgroundColor
import com.example.stocks.ui.design_system.LightGrayColor
import com.example.stocks.ui.design_system.NewsTextStyle

data class PinCode(
    val value: TextFieldValue,
    val pinCodeBlocksStyle: PinCodeBlockStyle
) {

    companion object {
        val EMPTY = PinCode(TextFieldValue(), PinCodeBlockStyle.DEFAULT)
    }
}

data class PinCodeBlockStyle(
    val textStyle: TextStyle,
    val blockColor: Color
) {

    companion object {
        val ERROR_STYLE = PinCodeBlockStyle(
            textStyle = NewsTextStyle.ConfirmationCodeBlockErrorText,
            blockColor = ErrorBackgroundColor
        )

        val HINT_STYLE = PinCodeBlockStyle(
            textStyle = NewsTextStyle.ConfirmationCodeBlockText,
            blockColor = LightGrayColor
        )

        val DEFAULT = HINT_STYLE
    }
}