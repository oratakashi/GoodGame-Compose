package com.oratakashi.goodgame.data.reqres.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import com.oratakashi.goodgame.data.reqres.web.RawgApi

class RecommendationPaging(
    private val api: RawgApi
) : PagingSource<Int, GamesItem>() {
    override fun getRefreshKey(state: PagingState<Int, GamesItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GamesItem> {
        return try {
            val currentPage = params.key ?: 1
            val data = api.getGamesDiscover(
                page = currentPage,
                pageSize = 10
            )

            LoadResult.Page(
                data = data.results ?: emptyList(),
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (data.results?.isEmpty() == true) null else currentPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(throwable = e)
        }
    }
}