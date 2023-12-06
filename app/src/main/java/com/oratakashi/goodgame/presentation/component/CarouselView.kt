package com.oratakashi.goodgame.presentation.component

import androidx.compose.animation.core.FastOutSlowInEasing
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.interaction.collectIsDraggedAsState
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.domain.model.games.Games
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun CarouselView(
    modifier: Modifier = Modifier,
    data: List<Games> = listOf()
) {
    val pageCount = Int.MAX_VALUE
    val pagerState = rememberPagerState(
        initialPage = pageCount / 2,
        initialPageOffsetFraction = 0f
    ) {
        pageCount
    }

    val isDragged by pagerState.interactionSource.collectIsDraggedAsState()

    var currentPageKey by remember { mutableIntStateOf(pageCount / 2) }
    if (isDragged.not()) {
        with(pagerState) {
            LaunchedEffect(key1 = currentPageKey) {
                launch {
                    delay(timeMillis = 3000)
                    val nextPage = (currentPage + 1).mod(pageCount)
                    animateScrollToPage(
                        page = nextPage,
                        animationSpec = tween(
                            durationMillis = 800,
                            easing = FastOutSlowInEasing
                        )
                    )
                    currentPageKey = nextPage
                }
            }
        }
    }

    HorizontalPager(
        state = pagerState,
        contentPadding = PaddingValues(horizontal = 16.dp),
        pageSpacing = 8.dp,
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
    ) { page ->
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .height(200.dp),
            colors = CardDefaults.cardColors(Color.White),
            shape = RoundedCornerShape(16.dp),
            elevation = CardDefaults.cardElevation(0.dp),
            border = BorderStroke(
                2.dp, Brush.verticalGradient(
                    startY = 10f,
                    colors = listOf(
                        Color.Transparent,
                        MaterialTheme.colorScheme.inversePrimary.copy(0.2f),
                        MaterialTheme.colorScheme.inversePrimary.copy(0.3f)
                    )
                )
            )
        ) {
            Box {
                AsyncImage(
                    model = ImageRequest.Builder(LocalContext.current)
                        .data(data[page % 3].backgroundImage)
                        .crossfade(true)
                        .build(),
                    contentDescription = null,
                    placeholder = painterResource(id = R.drawable.placeholder_portrait),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                )

                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            brush = Brush.verticalGradient(
                                startY = 10f,
                                colors = listOf(
                                    Color.Transparent,
                                    MaterialTheme.colorScheme.surface.copy(0.8f),
                                    MaterialTheme.colorScheme.surface
                                )
                            )
                        )
                ) {
                    Text(
                        text = data[page % 3].name,
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .align(Alignment.BottomStart)
                            .padding(16.dp),
                    )
                }
            }
        }

    }
}

@Composable
fun CarouselLoadingView() {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(200.dp)
            .horizontalScroll(rememberScrollState())
    ) {
        repeat(2) {
            Card(
                modifier = Modifier
                    .padding(
                        end = 8.dp,
                        start = if (it == 0) 16.dp else 0.dp
                    )
                    .width(360.dp)
                    .height(200.dp)
                    .shimmer(),
                colors = CardDefaults.cardColors(Color.Gray),
                shape = RoundedCornerShape(16.dp),
                elevation = CardDefaults.cardElevation(0.dp),
            ) {
                Box {

                }
            }
        }
    }
}

@Preview
@Composable
fun PreviewCarouselView() {
    GoodGameTheme {
        CarouselView(
            data = listOf(
                Games(
                    "https://media.rawg.io/media/screenshots/6e1/6e13d9acb4e7a6e184f24892f52c4544.jpg",
                    "The Witcher 2",
                    1,
                    "2016-08-30"
                ),
                Games(
                    "https://media.rawg.io/media/screenshots/6e1/6e13d9acb4e7a6e184f24892f52c4544.jpg",
                    "The Witcher 3",
                    2,
                    "2016-08-30"
                ),
                Games(
                    "https://media.rawg.io/media/screenshots/6e1/6e13d9acb4e7a6e184f24892f52c4544.jpg",
                    "The Witcher 4",
                    3,
                    "2016-08-30"
                )
            )
        )
    }
}

@Preview
@Composable
fun PreviewCarouselLoadingView() {
    GoodGameTheme {
        CarouselLoadingView()
    }
}