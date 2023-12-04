package com.oratakashi.goodgame.domain.model.games

import android.os.Parcelable
import androidx.annotation.Keep
import kotlinx.parcelize.Parcelize

@Keep
@Parcelize
data class Games(
    val backgroundImage: String,
    val name: String,
    val id: Int,
    val released: String
): Parcelable