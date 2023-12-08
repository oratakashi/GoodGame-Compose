package com.oratakashi.goodgame.presentation.menu.home

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridItemSpan
import androidx.compose.foundation.lazy.staggeredgrid.rememberLazyStaggeredGridState
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
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
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
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemKey
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.domain.model.menu.Exploration
import com.oratakashi.goodgame.presentation.component.CarouselLoadingView
import com.oratakashi.goodgame.presentation.component.CarouselView
import com.oratakashi.goodgame.presentation.component.ExploreView
import com.oratakashi.goodgame.presentation.component.GamesItemView
import com.oratakashi.goodgame.presentation.component.LottieView
import com.oratakashi.goodgame.presentation.component.MultiStateView
import com.oratakashi.goodgame.presentation.menu.destinations.GenreScreenDestination
import com.oratakashi.goodgame.presentation.navigation.MainNavGraph
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.oratakashi.goodgame.presentation.theme.SFPro
import com.oratakashi.goodgame.utilty.hasItems
import com.oratakashi.goodgame.utilty.isError
import com.oratakashi.goodgame.utilty.isRefreshing
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

            val recommendation = viewModel.recommendation.collectAsLazyPagingItems()

            val isLoading by remember(recommendation) {
                derivedStateOf {
                    recommendation.isRefreshing()
                }
            }

            val isHasItems by remember(recommendation) {
                derivedStateOf {
                    recommendation.hasItems()
                }
            }

            val isError by remember(recommendation) {
                derivedStateOf {
                    recommendation.isError()
                }
            }

            LazyVerticalStaggeredGrid(
                modifier = Modifier
                    .padding(innerPadding)
                    .padding(horizontal = 7.5.dp),
                state = rememberLazyStaggeredGridState(),
                columns = StaggeredGridCells.Adaptive(180.dp),
                verticalItemSpacing = 7.5.dp,
                horizontalArrangement = Arrangement.spacedBy(7.5.dp),
            ) {
                item("carousel", span = StaggeredGridItemSpan.FullLine) {
                    MultiStateView(
                        modifier = Modifier.fillMaxWidth(),
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
                }

                item("label_explore", span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = stringResource(R.string.label_explore),
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 7.5.dp)
                            .padding(top = 7.5.dp)
                    )
                }

                item("explore", span = StaggeredGridItemSpan.FullLine) {
                    ExploreView(
                        modifier = Modifier
                            .padding(top = 7.5.dp)
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
                }

                item("label_platform", span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = "Browse by Platforms",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 7.5.dp)
                            .padding(top = 7.5.dp)
                    )
                }

                item("platform", span = StaggeredGridItemSpan.FullLine) {
                    MultiStateView(
                        modifier = Modifier
                            .fillMaxWidth(),
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
                            contentPadding = PaddingValues(horizontal = 7.5.dp)
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

                item("label_recommendation", span = StaggeredGridItemSpan.FullLine) {
                    Text(
                        text = "Recommendation",
                        style = MaterialTheme.typography.titleMedium,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 7.5.dp)
                            .padding(top = 7.5.dp)
                    )
                }

                MultiStateView(
                    state = recommendation,
                    loadingLayout = {
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LottieView(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(150.dp)
                                    .align(Alignment.Center),
                                animation = R.raw.loading
                            )
                        }
                    },
                    errorLayout = { _, _ ->
                        Box(
                            modifier = Modifier.fillMaxWidth()
                        ) {
                            LottieView(
                                modifier = Modifier
                                    .width(150.dp)
                                    .height(150.dp)
                                    .align(Alignment.Center),
                                animation = R.raw.error
                            )
                        }
                    }
                ) {
                    it?.let { games ->
                        GamesItemView(
                            modifier = Modifier
                                .padding(horizontal = 7.5.dp, vertical = 7.5.dp),
                            games = games,
                            navController = navController
                        )
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