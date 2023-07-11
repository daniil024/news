package com.example.stocks.ui.design_system

import android.net.Uri
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.rememberAsyncImagePainter
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState

@OptIn(ExperimentalPagerApi::class)
@Composable
fun ImageCarousel(
    modifier: Modifier = Modifier,
    images: List<String>,
) {
    val pagerState = rememberPagerState(0)

    val selectImages by remember { mutableStateOf<List<String?>>(images) }

    Column(modifier = modifier) {
        Box(modifier = Modifier.weight(1f).padding(bottom = 8.dp)) {
            HorizontalPager(state = pagerState, count = selectImages.size) { index ->
                Image(
                    painter = rememberAsyncImagePainter(model = Uri.parse(selectImages[index])),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.None
                )
            }
        }

        if (selectImages.size > 1) {
            CustomLinearProgressIndicator(
                modifier = Modifier.fillMaxWidth(),
                progress = (pagerState.currentPage.toFloat() + 1) / selectImages.size,
            )
        }
    }
}

@Composable
fun CustomLinearProgressIndicator(
    modifier: Modifier? = Modifier,
    progress: Float
) {
    Box(
        modifier = Modifier
            .fillMaxWidth()
            .height(8.dp)
            .background(ContrastGrayColor)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth(progress)
                .height(8.dp)
                .background(ContrastDarkColor)
        )
    }
}