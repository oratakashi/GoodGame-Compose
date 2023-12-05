package com.oratakashi.goodgame.presentation.menu.genre

import androidx.compose.animation.rememberSplineBasedDecay
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.component.LottieView
import com.oratakashi.goodgame.component.MultiStateView
import com.oratakashi.goodgame.presentation.navigation.MainNavGraph
import com.oratakashi.goodgame.presentation.transition.SlideTransition
import com.ramcosta.composedestinations.annotation.Destination
import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@MainNavGraph
@Destination(
    style = SlideTransition::class
)
@Composable
fun GenreView(
    navController: NavController,
    viewModel: GenreViewModel = koinViewModel()
) {
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
    Scaffold(
        modifier = Modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            LargeTopAppBar(
                title = { Text(text = "Genre") },
                scrollBehavior = scrollBehavior,
                colors = TopAppBarDefaults.largeTopAppBarColors(
                    scrolledContainerColor = MaterialTheme.colorScheme.background
                ),
                navigationIcon = {
                    IconButton(onClick = {
                        navController.navigateUp()
                    }) {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null
                        )
                    }
                }
            )
        },
        content = {
            MultiStateView(
                state = viewModel.genres,
                modifier = Modifier.padding(it),
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
                },
                content = { data ->
                    LazyColumn {
                        items(data) {
                            GenreItemView(data = it, navController = navController)
                        }
                    }
                }
            )
        }
    )
}