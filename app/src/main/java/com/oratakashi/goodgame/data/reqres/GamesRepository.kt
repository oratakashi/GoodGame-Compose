package com.oratakashi.goodgame.data.reqres

import androidx.paging.PagingData
import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getGamesRelevance(): Flow<List<GamesItem>>

    fun getGamesRecommendation(): Flow<PagingData<GamesItem>>
}