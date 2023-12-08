package com.oratakashi.goodgame.data.reqres

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import com.oratakashi.goodgame.data.reqres.paging.RecommendationPaging
import com.oratakashi.goodgame.data.reqres.web.RawgApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GamesDataStore(
    private val web: RawgApi
) : GamesRepository {
    override fun getGamesRelevance(): Flow<List<GamesItem>> {
        return flow {
            emit(web.getGamesBanner().results ?: emptyList())
        }
    }

    override fun getGamesRecommendation(): Flow<PagingData<GamesItem>> {
        return Pager(
            config = PagingConfig(pageSize = 10),
            pagingSourceFactory = {
                RecommendationPaging(api = web)
            }
        ).flow
    }
}