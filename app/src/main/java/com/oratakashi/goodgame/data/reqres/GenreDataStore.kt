package com.oratakashi.goodgame.data.reqres

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import com.oratakashi.goodgame.data.reqres.model.genre.GenreItem
import com.oratakashi.goodgame.data.reqres.paging.GenrePaging
import com.oratakashi.goodgame.data.reqres.web.RawgApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenreDataStore @Inject constructor(
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