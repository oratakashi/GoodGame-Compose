package com.oratakashi.goodgame.presentation.menu.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.domain.model.games.Games
import com.oratakashi.goodgame.domain.model.menu.Exploration
import com.oratakashi.goodgame.presentation.component.CarouselLoadingView
import com.oratakashi.goodgame.presentation.component.CarouselView
import com.oratakashi.goodgame.presentation.component.ExploreView
import com.oratakashi.goodgame.presentation.component.LottieView
import com.oratakashi.goodgame.presentation.component.MultiStateView
import com.oratakashi.goodgame.presentation.menu.destinations.GenreScreenDestination
import com.oratakashi.goodgame.presentation.navigation.MainNavGraph
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.oratakashi.goodgame.presentation.theme.SFPro
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigate
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@MainNavGraph(start = true)
@Destination
@Composable
fun HomeScreen(
    navController: NavController,
    viewModel: HomeViewModel = koinViewModel()
) {
//    val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }
    val scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            CenterAlignedTopAppBar(
                title = {
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(text = "Good")
                        Text(
                            text = "Game",
                            style = TextStyle(
                                fontFamily = SFPro,
                                fontWeight = FontWeight.Normal,
                                fontSize = 22.sp,
                                lineHeight = 28.sp,
                                letterSpacing = 0.sp,
                            ),
                            modifier = Modifier
                                .padding(start = 5.dp)
                        )
                    }
                },
                actions = {
                    IconButton(onClick = { /* doSomething() */ }) {
                        Icon(
                            imageVector = Icons.Outlined.Search,
                            contentDescription = "Search"
                        )
                    }

                },
                scrollBehavior = scrollBehavior
            )
        },
        content = { innerPadding ->
            Column(
                modifier = Modifier
                    .padding(innerPadding)
                    .wrapContentSize(align = Alignment.TopStart)
                    .verticalScroll(rememberScrollState())
            ) {
                MultiStateView(
                    state = viewModel.banner,
                    loadingLayout = {
                        CarouselLoadingView()
                    },
                    emptyLayout = {
                        LottieView(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .align(Alignment.Center),
                            animation = R.raw.empty
                        )
                    },
                    errorLayout = { _, _ ->
                        LottieView(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .align(Alignment.Center),
                            animation = R.raw.error
                        )
                    }
                ) {
                    CarouselView(
                        data = it,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(
                                top = 16.dp,
                            )
                    )
                }
                val data = listOf(
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
//                CarouselLoadingView()
                Text(
                    text = stringResource(R.string.label_explore),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                )

                ExploreView(
                    modifier = Modifier
                        .fillMaxWidth()
                ) {
                    when (it) {
                        Exploration.NewRelease -> {

                        }

                        Exploration.TopRating -> {

                        }

                        Exploration.Genre -> {
                            navController.navigate(GenreScreenDestination)
                        }

                        Exploration.Publisher -> {

                        }
                    }
                }

                Text(
                    text = "Browse by Platforms",
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 7.5.dp)
                )

                MultiStateView(
                    modifier = Modifier
                        .padding(top = 7.5.dp),
                    state = viewModel.platforms,
                    loadingLayout = {
                        LottieView(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .align(Alignment.Center),
                            animation = R.raw.loading
                        )
                    },
                    emptyLayout = {
                        LottieView(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .align(Alignment.Center),
                            animation = R.raw.empty
                        )
                    },
                    errorLayout = { _, _ ->
                        LottieView(
                            modifier = Modifier
                                .width(150.dp)
                                .height(150.dp)
                                .align(Alignment.Center),
                            animation = R.raw.error
                        )
                    }
                ) { data ->
                    LazyRow(
                        contentPadding = PaddingValues(horizontal = 16.dp)
                    ) {
                        items(data.size) { position ->
                            PlatformView(
                                platforms = data[position]
                            )
                        }
                        item {
                            PlatformSeeAllView()
                        }
                    }
                }
            }
        }
    )
}


@Preview
@Composable
fun PreviewHomeView() {
    GoodGameTheme {
        val navController = rememberNavController()
        HomeScreen(navController)
    }
}