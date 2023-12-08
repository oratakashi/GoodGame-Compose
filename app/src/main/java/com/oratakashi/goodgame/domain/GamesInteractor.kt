package com.oratakashi.goodgame.domain

import androidx.paging.PagingData
import androidx.paging.map
import com.oratakashi.goodgame.data.reqres.GamesRepository
import com.oratakashi.goodgame.domain.model.games.Games
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GamesInteractor(
    private val repo: GamesRepository
) : GamesUsecase {
    override fun getGamesRelevance(): Flow<List<Games>> {
        return repo.getGamesRelevance().map { it.map { data -> data.toGames() } }
    }

    override fun getGamesRecommendation(): Flow<PagingData<Games>> {
        return repo.getGamesRecommendation().map { it.map { data -> data.toGames() } }
    }
}