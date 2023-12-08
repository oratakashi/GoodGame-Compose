package com.oratakashi.goodgame.domain

import androidx.paging.PagingData
import com.oratakashi.goodgame.domain.model.games.Games
import kotlinx.coroutines.flow.Flow

interface GamesUsecase {
    fun getGamesRelevance(): Flow<List<Games>>

    fun getGamesRecommendation(): Flow<PagingData<Games>>
}