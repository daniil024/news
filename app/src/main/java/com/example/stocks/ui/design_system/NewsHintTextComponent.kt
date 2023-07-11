package com.example.stocks.ui.design_system

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import com.example.stocks.ui.design_system.NewsTextStyle.Hint14spDim

@Composable
fun HintText(
    modifier: Modifier = Modifier,
    textStyle: TextStyle = Hint14spDim,
    textValue: String,
) {
    Text(
        modifier = modifier,
        text = textValue,
        style = textStyle
    )
}