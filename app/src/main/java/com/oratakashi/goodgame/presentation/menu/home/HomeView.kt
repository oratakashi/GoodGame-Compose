package com.oratakashi.goodgame.presentation.menu.home

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.ThumbUp
import androidx.compose.material.icons.outlined.Search
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.component.LottieView
import com.oratakashi.goodgame.component.MultiStateView
import com.oratakashi.goodgame.presentation.menu.destinations.GenreViewDestination
import com.oratakashi.goodgame.presentation.navigation.MainNavGraph
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.oratakashi.goodgame.presentation.theme.SFPro
import com.oratakashi.goodgame.utilty.gridItems
import com.ramcosta.composedestinations.annotation.Destination
import com.ramcosta.composedestinations.navigation.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
@MainNavGraph(start = true)
@Destination
@Composable
fun HomeView(
    navController: NavController,
    viewModel: HomeViewModel = hiltViewModel()
) {
    val scrollBehavior = remember { TopAppBarDefaults.enterAlwaysScrollBehavior() }
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
                Text(
                    text = stringResource(R.string.label_explore),
                    style = MaterialTheme.typography.titleMedium,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(horizontal = 16.dp)
                        .padding(top = 16.dp)
                )

                Row(
                    modifier = Modifier
                        .padding(top = 7.5.dp)
                ) {
                    ExploreItemView(
                        data = painterResource(id = R.drawable.ic_new) to "New Release",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 7.5.dp, end = 2.5.dp)
                    )
                    ExploreItemView(
                        data = painterResource(id = R.drawable.ic_top) to "Top Rating",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 2.5.dp, end = 7.5.dp)
                    )
                }

                Row(
                    modifier = Modifier
                        .padding(top = 2.5.dp)
                ) {
                    ExploreItemView(
                        data = painterResource(id = R.drawable.ic_genre) to "Genre",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 7.5.dp, end = 2.5.dp)
                            .clickable {
                                navController.navigateTo(GenreViewDestination)
                            }
                    )
                    ExploreItemView(
                        data = painterResource(id = R.drawable.ic_platform) to "Publisher",
                        modifier = Modifier
                            .weight(1f)
                            .padding(start = 2.5.dp, end = 7.5.dp)
                    )
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
                                navController = navController,
                                platforms = data[position]
                            )
                        }
                        item { 
                            PlatformSeeAllView(navController = navController)
                        }
                    }
                }
            }
        }
    )
}

@Composable
fun ExploreItemView(
    data: Pair<Painter, String>,
    modifier: Modifier = Modifier
) {
    ConstraintLayout(
        modifier = modifier
            .fillMaxWidth()
            .padding(7.5.dp)
            .clip(RoundedCornerShape(16.dp))
            .background(MaterialTheme.colorScheme.primaryContainer)
            .padding(16.dp)
    ) {
        val (ivIcon, tvTitle) = createRefs()
        Icon(
            painter = data.first,
            contentDescription = data.second,
            modifier = Modifier
                .constrainAs(ivIcon) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    bottom.linkTo(parent.bottom)
                    width = Dimension.wrapContent
                    height = Dimension.wrapContent
                }
        )

        Text(
            text = data.second,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(start = 16.dp)
                .constrainAs(tvTitle) {
                    top.linkTo(parent.top)
                    start.linkTo(ivIcon.end)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)

                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
        )
    }
}

@Preview
@Composable
fun PreviewExploreItemView() {
    ExploreItemView(
        data = painterResource(id = R.drawable.ic_top) to "Favorite"
    )
}

@Preview
@Composable
fun PreviewHomeView() {
    GoodGameTheme {
        val navController = rememberNavController()
        HomeView(navController)
    }
}