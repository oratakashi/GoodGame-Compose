package com.oratakashi.goodgame.presentation.menu.main

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Home
import androidx.compose.material.icons.rounded.FavoriteBorder
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
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
import com.ramcosta.composedestinations.navigation.navigateTo

@OptIn(ExperimentalMaterial3Api::class)
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
                    Icons.Rounded.FavoriteBorder
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

    NavigationBar {
        items.forEachIndexed { index, item ->
            NavigationBarItem(
                icon = { Icon(item.icon, contentDescription = null) },
                label = { Text(item.title) },
                selected = selectedItem == item.destination,
                onClick = {
                    if (index == 0) {
                        navController.navigateTo(HomeViewDestination) {
                            launchSingleTop = true
                            popUpTo(HomeViewDestination.route)
                        }
                    } else {
                        navController.navigateTo(FavoriteViewDestination) {
                            launchSingleTop = true
                        }
                    }
                }
            )
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