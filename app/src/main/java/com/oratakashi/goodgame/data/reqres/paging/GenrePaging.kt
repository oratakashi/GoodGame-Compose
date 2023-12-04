package com.oratakashi.goodgame.data.reqres.paging

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.oratakashi.goodgame.data.reqres.model.genre.GenreItem
import com.oratakashi.goodgame.data.reqres.web.RawgApi

class GenrePaging constructor(
    private val api: RawgApi
) : PagingSource<Int, GenreItem>() {
    override fun getRefreshKey(state: PagingState<Int, GenreItem>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, GenreItem> {
        return try {
            val dataList = api.getGenres()
            val nextPage = params.key ?: 1
            LoadResult.Page(
                data = dataList.results ?: emptyList(),
                prevKey = null,
                nextKey = if (dataList.results?.isEmpty() == true) null else nextPage + 1
            )
        } catch (e: Exception) {
            LoadResult.Error(e)
        }
    }
}