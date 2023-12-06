package com.oratakashi.goodgame.data.reqres

import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import com.oratakashi.goodgame.data.reqres.web.RawgApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GamesDataStore(
    private val web: RawgApi
) : GamesRepository {
    override fun getGamesRelevance(): Flow<List<GamesItem>> {
        return flow {
            emit(web.getGamesRelevance().results ?: emptyList())
        }
    }
}