package com.oratakashi.goodgame.data.reqres.web

import com.oratakashi.goodgame.data.reqres.model.ResponseData
import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import com.oratakashi.goodgame.data.reqres.model.genre.GenreItem
import com.oratakashi.goodgame.data.reqres.model.platforms.PlatformsItem

class RawgApi constructor(
    private val api: RawgApiClient
) : RawgApiClient {
    override suspend fun getGenres(
        ordering: String
    ): ResponseData<List<GenreItem>> {
        return api.getGenres()
    }

    override suspend fun getGamesByGenre(
        genre: Int,
        pageSize: Int,
        ordering: String
    ): ResponseData<List<GamesItem>> {
        return api.getGamesByGenre(genre)
    }

    override suspend fun getPlatforms(page: Int, pageSize: Int): ResponseData<List<PlatformsItem>> {
        return api.getPlatforms(page)
    }

    override suspend fun getGamesRelevance(
        ordering: String,
        date: String,
        page: Int,
        pageSize: Int
    ): ResponseData<List<GamesItem>> {
        return api.getGamesRelevance()
    }
}