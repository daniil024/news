package com.example.stocks.ui.design_system

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp

object NewsTextStyle {

    val ScreenHeader1 = TextStyle(
        color = Color.Black,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold
    )

    val TextClarification = TextStyle(
        color = ContrastDarkColor,
        fontSize = 16.sp,
        lineHeight = 22.sp
    )

    val Hint14spDim = TextStyle(
        color = LightBrownColor,
        fontSize = 14.sp,
        lineHeight = 18.sp
    )

    val ConfirmationCodeBlockTextPlaceholder = TextStyle(
        color = ContrastGrayColor,
        fontSize = 38.sp,
        fontWeight = FontWeight.Bold
    )

    val ErrorHint14sp = TextStyle(
        color = ErrorTextColor,
        fontSize = 14.sp,
        lineHeight = 20.sp,
        letterSpacing = (0.5).sp
    )

    val ConfirmationCodeBlockText = TextStyle(
        color = ContrastDarkColor,
        fontSize = 38.sp,
        fontWeight = FontWeight.Bold
    )

    val ConfirmationCodeBlockErrorText = TextStyle(
        color = ErrorTextColor,
        fontSize = 38.sp,
        fontWeight = FontWeight.Bold
    )
}