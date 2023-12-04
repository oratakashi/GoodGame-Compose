package com.oratakashi.goodgame.domain.model.genre

data class Genre(
    val gamesCount: Int,
    val name: String,
    val id: Int,
    val imageBackground: String,
    val slug: String
)