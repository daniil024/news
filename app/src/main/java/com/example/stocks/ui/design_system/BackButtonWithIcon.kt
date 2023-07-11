package com.example.stocks.ui.design_system

import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavHostController

@Composable
fun BackButtonWithIcon(
    modifier: Modifier = Modifier,
    navHostController: NavHostController
) {
    IconButton(modifier = modifier, onClick = { navHostController.popBackStack() }) {
        Icon(painter = painterResource(id = NewsIcons.BackArrow), contentDescription = null)
    }
}