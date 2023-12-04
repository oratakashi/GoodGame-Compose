package com.oratakashi.goodgame.presentation.menu.genre

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import coil.compose.AsyncImage
import coil.request.ImageRequest
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.domain.model.games.Games
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme
import com.oratakashi.goodgame.utilty.addEmptyLines

@Composable
fun GamesItemView(
    games: Games,
    navController: NavController
) {
    ConstraintLayout(
        modifier = Modifier
            .padding(end = 10.dp)
            .width(120.dp)
            .height(180.dp)
            .clip(RoundedCornerShape(8.dp))
            .clickable {

            }
    ) {
        val (ivImage, tvTitle) = createRefs()

        AsyncImage(
            model = ImageRequest.Builder(LocalContext.current)
                .data(games.backgroundImage)
                .crossfade(true)
                .build(),
            contentDescription = games.name,
            placeholder = painterResource(id = R.drawable.placeholder_portrait),
            contentScale = ContentScale.Crop,
            modifier = Modifier
                .constrainAs(ivImage) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(parent.bottom)

                    width = Dimension.fillToConstraints
                    height = Dimension.fillToConstraints
                }
        )

        Text(
            text = games.name.addEmptyLines(1),
            style = MaterialTheme.typography.bodyMedium,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis,
            modifier = Modifier
                .background(MaterialTheme.colorScheme.surface.copy(0.9f))
                .padding(5.dp)
                .constrainAs(tvTitle) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)

                    width = Dimension.fillToConstraints
                    height = Dimension.wrapContent
                }
        )
    }
}

@Preview
@Composable
fun PreviewGamesItemView() {
    GoodGameTheme {
        val navController = rememberNavController()
        GamesItemView(
            games = Games(
                "https://media.rawg.io/media/screenshots/6e1/6e13d9acb4e7a6e184f24892f52c4544.jpg",
                "The Witcher 3: Game of the Year",
                1,
                "2016-08-30"
            ), navController = navController
        )
    }
}