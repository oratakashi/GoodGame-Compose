package com.oratakashi.goodgame.presentation.component

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.oratakashi.goodgame.R
import com.oratakashi.goodgame.domain.model.menu.Exploration
import com.oratakashi.goodgame.presentation.theme.GoodGameTheme


@Composable
fun ExploreView(
    modifier: Modifier = Modifier,
    onClick: (Exploration) -> Unit = {}
) {
    Column(
        modifier = modifier
    ) {
        Row(
            modifier = Modifier
        ) {
            ExploreItemView(
                data = painterResource(id = R.drawable.ic_new) to stringResource(R.string.label_new_release),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 0.dp, end = 1.0.dp)
            )
            ExploreItemView(
                data = painterResource(id = R.drawable.ic_top) to stringResource(R.string.label_top_rating),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 1.0.dp, end = 0.dp)
            )
        }

        Row(
            modifier = Modifier
                .padding(top = 1.dp)
        ) {
            ExploreItemView(
                data = painterResource(id = R.drawable.ic_genre) to stringResource(R.string.label_genre),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 0.dp, end = 1.dp)
                    .clickable {
                        onClick.invoke(Exploration.Genre)
                    }
            )
            ExploreItemView(
                data = painterResource(id = R.drawable.ic_platform) to stringResource(R.string.label_publisher),
                modifier = Modifier
                    .weight(1f)
                    .padding(start = 1.dp, end = 0.dp)
            )
        }
    }
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
fun PreviewExploreView() {
    GoodGameTheme {
        ExploreView()
    }
}

@Preview
@Composable
fun PreviewExploreItemView() {
    GoodGameTheme {
        ExploreItemView(
            data = painterResource(id = R.drawable.ic_top) to "Favorite"
        )
    }
}