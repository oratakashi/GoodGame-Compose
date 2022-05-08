package com.oratakashi.goodgame.data.reqres

import androidx.paging.PagingData
import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import com.oratakashi.goodgame.data.reqres.model.genre.GenreItem
import kotlinx.coroutines.flow.Flow

interface GenreRepository {
    fun getGenre(): Flow<List<GenreItem>>
    fun getGamesByGenre(genre: Int): Flow<List<GamesItem>>
}