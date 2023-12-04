package com.oratakashi.goodgame.presentation.menu.genre

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.component.LottieView
import com.oratakashi.goodgame.component.MultiStateView
import com.oratakashi.goodgame.domain.model.genre.Genre
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.valentinilk.shimmer.shimmer
import kotlinx.coroutines.flow.asStateFlow
import org.koin.androidx.compose.koinViewModel

@Composable
fun GenreItemView(
    modifier: Modifier = Modifier,
    viewModel: GenreViewModel = koinViewModel(),
    data: Genre,
    navController: NavController
) {
    val refreshVM = viewModel.refresh.collectAsState(initial = 1)
    var refresh by remember {
        mutableStateOf(-1)
    }
    var calledApi by remember {
        mutableStateOf(false)
    }

    if (refresh != refreshVM.value) {
        refresh = refreshVM.value
        SideEffect {
            Log.e("debug", "debug: $refresh")
        }
    }
    Surface {
        ConstraintLayout(
            modifier = modifier
                .fillMaxWidth()
        ) {
            val (tvTitle, tvSeeAll, msvContent) = createRefs()

            Text(
                text = data.name,
                style = MaterialTheme.typography.titleMedium,
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .constrainAs(tvTitle) {
                        top.linkTo(parent.top)
                        start.linkTo(parent.start)
                        end.linkTo(tvSeeAll.start)
                        width = Dimension.fillToConstraints
                    }
            )

            Text(
                text = stringResource(R.string.label_see_all),
                style = MaterialTheme.typography.titleSmall,
                color = MaterialTheme.colorScheme.primary,
                modifier = Modifier
                    .padding(end = 16.dp)
                    .constrainAs(tvSeeAll) {
                        top.linkTo(tvTitle.top)
                        bottom.linkTo(tvTitle.bottom)
                        end.linkTo(parent.end)
                    }
            )

            SideEffect {
                if (!calledApi) {
                    viewModel.getGamesByGenre(data.id)
                    calledApi = true
                }
            }

            val state = viewModel.games.value.firstOrNull { it.first == data.id }

            if (state != null) {
                MultiStateView(
                    state = state.second.asStateFlow(),
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .constrainAs(msvContent) {
                            top.linkTo(tvTitle.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
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
                    },
                    loadingLayout = {
                        LoadingGamesView()
                    },
                    content = { data ->
                        LazyRow(
                            contentPadding = PaddingValues(horizontal = 16.dp)
                        ) {
                            items(data.size) { position ->
                                GamesItemView(
                                    games = data[position],
                                    navController = navController
                                )
                            }
                        }
                    }
                )
            } else {
                LoadingGamesView(
                    modifier = Modifier
                        .padding(top = 16.dp, bottom = 16.dp)
                        .constrainAs(msvContent) {
                            top.linkTo(tvTitle.bottom)
                            start.linkTo(parent.start)
                            end.linkTo(parent.end)

                            width = Dimension.fillToConstraints
                            height = Dimension.wrapContent
                        }
                )
            }
        }
    }
}

@Composable
internal fun LoadingGamesView(
    modifier: Modifier = Modifier
) {
    LazyRow(
        modifier = modifier,
        contentPadding = PaddingValues(start = 16.dp)
    ) {
        items(5) {
            Box(
                modifier = Modifier
                    .padding(end = 16.dp)
                    .width(120.dp)
                    .height(180.dp)
                    .shimmer()
                    .background(Color.Gray)
            )
        }
    }
}

@Preview
@Composable
fun PreviewLoadingGamesView() {
    GoodGameTheme {
        LoadingGamesView()
    }
}

@Preview
@Composable
fun PreviewHomeItemView() {
    GoodGameTheme {
        val navController = rememberNavController()
        GenreItemView(
            data = Genre(
                10,
                "Action",
                1,
                "",
                ""
            ),
            navController = navController
        )
    }
}