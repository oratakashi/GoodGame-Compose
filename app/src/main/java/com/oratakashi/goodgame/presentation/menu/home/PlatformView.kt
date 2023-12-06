package com.oratakashi.goodgame.presentation.menu.home

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PlatformView(
    platforms: Platforms
) {
    Card(
        modifier = Modifier
            .padding(end = 8.dp)
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