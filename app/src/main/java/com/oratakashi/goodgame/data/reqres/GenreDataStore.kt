package com.oratakashi.goodgame.data.reqres

import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import com.oratakashi.goodgame.data.reqres.model.genre.GenreItem
import com.oratakashi.goodgame.data.reqres.web.RawgApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class GenreDataStore(
    private val web: RawgApi
) : GenreRepository {

    override fun getGenre(): Flow<List<GenreItem>> {
        return flow {
            emit(web.getGenres().results ?: emptyList())
        }.flowOn(Dispatchers.IO)
    }

    override fun getGamesByGenre(genre: Int): Flow<List<GamesItem>> {
        return flow {
            emit(web.getGamesByGenre(genre).results ?: emptyList())
        }.flowOn(Dispatchers.IO)
    }
}