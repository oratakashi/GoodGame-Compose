package com.oratakashi.goodgame.presentation.menu.fav

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import com.oratakashi.goodgame.presentation.navigation.MainNavGraph
import com.ramcosta.composedestinations.annotation.Destination

@MainNavGraph
@Destination
@Composable
fun FavoriteView() {
    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Text(
            text = "Favorite",
            modifier = Modifier
                .align(Alignment.Center)
        )
    }
}