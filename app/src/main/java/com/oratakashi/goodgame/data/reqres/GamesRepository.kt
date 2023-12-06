package com.oratakashi.goodgame.data.reqres

import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import kotlinx.coroutines.flow.Flow

interface GamesRepository {
    fun getGamesRelevance(): Flow<List<GamesItem>>
}