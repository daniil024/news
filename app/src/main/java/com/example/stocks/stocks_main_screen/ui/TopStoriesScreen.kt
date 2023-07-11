package com.example.stocks.stocks_main_screen.ui

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.pullrefresh.PullRefreshIndicator
import androidx.compose.material.pullrefresh.pullRefresh
import androidx.compose.material.pullrefresh.rememberPullRefreshState
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavHostController
import com.example.stocks.ui.design_system.ShimmerColor
import com.google.accompanist.placeholder.PlaceholderHighlight
import com.google.accompanist.placeholder.material.shimmer
import com.google.accompanist.placeholder.placeholder
import com.skydoves.landscapist.ImageOptions
import com.skydoves.landscapist.glide.GlideImage
import kotlinx.coroutines.flow.update
import org.koin.androidx.compose.koinViewModel
import org.koin.core.parameter.parametersOf

@Composable
fun ArticlesRoute(
    navHostController: NavHostController,
    viewModel: TopStoriesViewModel = koinViewModel { parametersOf(navHostController) }
) {
    ArticlesScreen(viewModel)
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun ArticlesScreen(viewModel: TopStoriesViewModel) {
    val refreshing by viewModel.isLoading.collectAsStateWithLifecycle()
    val pullRefreshState = rememberPullRefreshState(refreshing, { viewModel.swipedToRefresh() })
    val topStories by viewModel.articlesFlow.collectAsStateWithLifecycle()

    Column {
        ErrorBar(viewModel)

        Box(
            modifier = Modifier
                .padding(12.dp, 0.dp, 12.dp, 0.dp)
                .fillMaxSize()
                .pullRefresh(pullRefreshState)
        ) {
            LazyColumn(contentPadding = PaddingValues(top = 12.dp)) {
                items(topStories.news) {
                    ArticlePreview(
                        modifier = Modifier,
                        imageUrl = if (it.multimedia?.isNotEmpty() == true) it.multimedia[0].url else null,
                        title = it.title,
                        onArticleClicked = { viewModel.onArticleClicked(it) },
                        viewModel = viewModel
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                }
            }

            PullRefreshIndicator(refreshing, pullRefreshState, Modifier.align(Alignment.TopCenter))
        }
    }
}

@Composable
fun ErrorBar(viewModel: TopStoriesViewModel) {
    val errorMessage by viewModel.responseErrorStateFlow.collectAsStateWithLifecycle()

    AnimatedVisibility(
        visible = errorMessage != "",
        enter = fadeIn(animationSpec = tween(durationMillis = 500, easing = LinearEasing)),
        exit = fadeOut(animationSpec = tween(durationMillis = 500))
    ) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Red)
                .padding(vertical = 14.dp, horizontal = 8.dp),
            text = errorMessage,
            color = Color.White,
            fontSize = 20.sp,
            textAlign = TextAlign.Center,
            overflow = TextOverflow.Ellipsis
        )
    }
}

@Composable
fun ArticlePreview(
    modifier: Modifier = Modifier,
    imageUrl: String?,
    title: String,
    onArticleClicked: () -> Unit,
    viewModel: TopStoriesViewModel
) {

    val placeholderVisible by viewModel.isLoading.collectAsStateWithLifecycle()

    Row(modifier = modifier
        .fillMaxWidth()
        .placeholder(
            visible = placeholderVisible,
            shape = RoundedCornerShape(28.dp),
            color = ShimmerColor,
            highlight = PlaceholderHighlight.shimmer()
        )
        .clickable(enabled = !placeholderVisible) { onArticleClicked() }) {
        GlideImage(
            imageModel = { imageUrl },
            modifier = Modifier
                .size(90.dp)
                .clip(RoundedCornerShape(28.dp)),
            imageOptions = ImageOptions(
                contentScale = ContentScale.Crop,
                alignment = Alignment.Center
            ),
            loading = {
                Box(
                    modifier = modifier
                        .fillMaxSize()
                        .placeholder(
                            visible = placeholderVisible,
                            shape = RoundedCornerShape(28.dp),
                            color = ShimmerColor,
                            highlight = PlaceholderHighlight.shimmer()
                        )
                )
            },
            failure = {
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(Color.LightGray)
                ) {
                    Text(text = "error", modifier = Modifier.align(Alignment.Center))
                }
            }
        )
        Text(
            modifier = Modifier
                .padding(start = 6.dp)
                .align(Alignment.CenterVertically), text = title, textAlign = TextAlign.Justify
        )
    }
}