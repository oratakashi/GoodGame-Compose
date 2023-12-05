package com.oratakashi.goodgame.presentation.menu.main

import android.util.Log
import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.tween
import androidx.compose.animation.slideIn
import androidx.compose.animation.slideInVertically
import androidx.compose.animation.slideOut
import androidx.compose.animation.slideOutVertically
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.NavOptionsBuilder
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.presentation.menu.NavGraphs
import com.oratakashi.goodgame.presentation.menu.appDestination
import com.oratakashi.goodgame.presentation.menu.destinations.Destination
import com.oratakashi.goodgame.presentation.menu.destinations.FavoriteViewDestination
import com.oratakashi.goodgame.presentation.menu.destinations.HomeViewDestination
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.navigation.navigate
import com.ramcosta.composedestinations.rememberNavHostEngine

@Composable
fun MainView() {
    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNavBar(
                navController,
                NavigationItem(
                    stringResource(R.string.label_home),
                    HomeViewDestination,
                    Icons.Outlined.Home
                ),
                NavigationItem(
                    stringResource(R.string.label_favorite),
                    FavoriteViewDestination,
                    Icons.Outlined.FavoriteBorder
                )
            )
        }
    ) {
        DestinationsNavHost(
            navController = navController,
            navGraph = NavGraphs.root,
            modifier = Modifier.padding(it)
        )
    }
}

@Composable
fun BottomNavBar(
    navController: NavController,
    vararg items: NavigationItem
) {
    val selectedItem: Destination? = navController.currentBackStackEntryAsState()
        .value?.appDestination()

    AnimatedVisibility(
        visible = (selectedItem == HomeViewDestination || selectedItem == FavoriteViewDestination),
        enter = slideInVertically(
            initialOffsetY = { 300 },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing // interpolator
            )
        ),
        exit = slideOutVertically(
            targetOffsetY = { 300 },
            animationSpec = tween(
                durationMillis = 500,
                easing = LinearEasing // interpolator
            )
        )
    ) {
        NavigationBar(
            modifier = Modifier
        ) {
            items.forEachIndexed { index, item ->
                NavigationBarItem(
                    icon = { Icon(item.icon, contentDescription = null) },
                    label = { Text(item.title) },
                    selected = selectedItem == item.destination,
                    onClick = {
                        if (index == 0) {
                            navController.navigate(HomeViewDestination, fun NavOptionsBuilder.() {
                                launchSingleTop = true
                                popUpTo(HomeViewDestination.route)
                            })
                        } else {
                            navController.navigate(FavoriteViewDestination, fun NavOptionsBuilder.() {
                                launchSingleTop = true
                            })
                        }
                    }
                )
            }
        }
    }
}

data class NavigationItem(
    val title: String,
    val destination: Destination,
    val icon: ImageVector
)

@Preview
@Composable
fun PreviewMainView() {
    GoodGameTheme {
        MainView()
    }
}