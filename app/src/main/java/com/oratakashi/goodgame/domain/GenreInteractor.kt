package com.oratakashi.goodgame.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.oratakashi.goodgame.data.reqres.GenreRepository
import com.oratakashi.goodgame.domain.model.games.Games
import com.oratakashi.goodgame.domain.model.genre.Genre
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class GenreInteractor @Inject constructor(
    private val repo: GenreRepository
): GenreUsecase {
    override fun getGenre(): Flow<List<Genre>> {
        return repo.getGenre().map { it.map { data -> data.toGenre() } }
    }

    override fun getGamesByGenre(genre: Int): Flow<List<Games>> {
        return repo.getGamesByGenre(genre).map { it.map { data -> data.toGames() } }
    }
}