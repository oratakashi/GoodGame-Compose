package com.oratakashi.goodgame.presentation.menu.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.domain.model.platforms.Platforms
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.valentinilk.shimmer.shimmer

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformView(
    platforms: Platforms
) {
    Card(
        modifier = Modifier
            .padding(end = 7.5.dp)
            .clip(RoundedCornerShape(20.dp)),
        onClick = {

        }
    ) {
        Text(
            text = platforms.name,
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 1,
            overflow = TextOverflow.Clip,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
}

@Composable
fun PlatformsLoading() {
    Row {
        repeat(5) {
            Card(
                modifier = Modifier
                    .height(40.dp)
                    .width(100.dp)
                    .padding(end = 8.dp)
                    .clip(RoundedCornerShape(12.dp))
                    .background(Color.Gray)
                    .shimmer()
            ) {
                Box {

                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformSeeAllView() {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
            .clip(RoundedCornerShape(20.dp)),
        onClick = {

        }
    ) {
        Text(
            text = stringResource(id = R.string.label_see_all),
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(horizontal = 20.dp, vertical = 8.dp)
        )
    }
}

@Preview
@Composable
fun PreviewPlatformSeeAll() {
    GoodGameTheme {
        val navController = rememberNavController()
        PlatformSeeAllView()
    }
}

@Preview
@Composable
fun PreviewPlatform() {
    GoodGameTheme {
        PlatformView(
            Platforms(
                "PC",
                1
            )
        )
    }
}

@Preview
@Composable
fun PreviewPlatformLoading() {
    GoodGameTheme {
        PlatformsLoading()
    }
    
}