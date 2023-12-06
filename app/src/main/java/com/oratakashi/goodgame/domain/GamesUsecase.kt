package com.oratakashi.goodgame.domain

import com.oratakashi.goodgame.domain.model.games.Games
import kotlinx.coroutines.flow.Flow

interface GamesUsecase {
    fun getGamesRelevance(): Flow<List<Games>>
}