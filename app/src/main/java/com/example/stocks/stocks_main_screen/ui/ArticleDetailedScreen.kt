package com.example.stocks.stocks_main_screen.ui

import android.annotation.SuppressLint
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.FiniteAnimationSpec
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Chip
import androidx.compose.material.ChipDefaults
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import com.example.stocks.ui.design_system.BackButtonWithIcon
import com.example.stocks.ui.design_system.GrayBorderColor
import com.example.stocks.ui.design_system.LightBrownColor
import com.skydoves.landscapist.glide.GlideImage
import org.koin.androidx.compose.koinViewModel

@Composable
fun ArticleDetailedRoute(
    navHostController: NavHostController,
    viewModel: DetailedArticleViewModel = koinViewModel()
) {
    ArticleDetailedScreen(viewModel, navHostController)
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun ArticleDetailedScreen(
    viewModel: DetailedArticleViewModel,
    navHostController: NavHostController
) {
    val article = viewModel.openedArticle

    Column(
        modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween) {
            BackButtonWithIcon(
                modifier = Modifier.offset(x = (-12).dp),
                navHostController = navHostController
            )
            SectionChip(section = article.section)
        }

        Text(
            text = article.title,
            modifier = Modifier.padding(top = 8.dp),
            fontSize = 28.sp,
            fontWeight = FontWeight.ExtraBold,
            textAlign = TextAlign.Center
        )

        ArticlePhoto(url = article.multimedia?.get(0)?.url)

        Text(
            text = article.abstractDescription,
            fontSize = 18.sp,
            textAlign = TextAlign.Justify
        )
        Text(
            text = article.publishDate.makeArticlePublishDate(),
            modifier = Modifier
                .align(Alignment.End)
                .padding(top = 12.dp),
            fontSize = 14.sp,
            fontWeight = FontWeight.Black
        )
    }
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
internal fun SectionChip(modifier: Modifier = Modifier, section: String) {
    Chip(
        modifier = modifier,
        onClick = {},
        border = BorderStroke(1.dp, GrayBorderColor),
        shape = RoundedCornerShape(10.dp),
        colors = ChipDefaults.outlinedChipColors()
    ) {
        androidx.compose.material.Text(
            text = section,
            fontSize = 16.sp,
            color = LightBrownColor
        )
    }
}

@Composable
internal fun ArticlePhoto(url: String?) {
    val animationSpec: FiniteAnimationSpec<Float> = tween(
        durationMillis = 500,
        easing = LinearEasing
    )
    AnimatedVisibility(
        visible = true,
        enter = fadeIn(animationSpec = animationSpec),
        exit = fadeOut(animationSpec = animationSpec)
    ) {
        GlideImage(
            imageModel = { url },
            modifier = Modifier
                .heightIn(0.dp, 400.dp)
                .padding(vertical = 16.dp)
                .clip(RoundedCornerShape(42.dp))
                .wrapContentSize()
        )
    }
}