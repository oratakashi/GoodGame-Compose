package com.oratakashi.goodgame.presentation.menu.genre

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LargeTopAppBar
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.presentation.component.LottieView
import com.oratakashi.goodgame.presentation.component.MultiStateView
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
fun GenreScreen(
    navController: NavController,
    viewModel: GenreViewModel = koinViewModel()
) {
    val scrollBehavior =
        TopAppBarDefaults.exitUntilCollapsedScrollBehavior(rememberTopAppBarState())
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