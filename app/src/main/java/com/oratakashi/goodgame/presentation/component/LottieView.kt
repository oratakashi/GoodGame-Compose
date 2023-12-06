package com.oratakashi.goodgame.presentation.component

import androidx.annotation.RawRes
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable

@Composable
fun LottieView(
    modifier: Modifier = Modifier,
    @RawRes animation: Int
) {
    AndroidView(
        modifier = modifier,
        factory = {
            LottieAnimationView(it).apply {
                repeatMode = LottieDrawable.RESTART
                setAnimation(animation)
                playAnimation()
            }
        }
    )
}