package com.oratakashi.goodgame.domain

import com.oratakashi.goodgame.domain.model.games.Games
import com.oratakashi.goodgame.domain.model.genre.Genre
import kotlinx.coroutines.flow.Flow

interface GenreUsecase {
    fun getGenre(): Flow<List<Genre>>
    fun getGamesByGenre(genre: Int): Flow<List<Games>>
}