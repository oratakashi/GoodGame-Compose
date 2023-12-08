package com.oratakashi.goodgame.data.reqres.web

import com.oratakashi.goodgame.data.reqres.model.ResponseData
import com.oratakashi.goodgame.data.reqres.model.games.GamesItem
import com.oratakashi.goodgame.data.reqres.model.genre.GenreItem
import com.oratakashi.goodgame.data.reqres.model.platforms.PlatformsItem
import retrofit2.http.GET
import retrofit2.http.Query

interface RawgApiClient {
    @GET("genres")
    suspend fun getGenres(
        @Query("ordering") ordering: String = "name"
    ): ResponseData<List<GenreItem>>

    @GET("games")
    suspend fun getGamesByGenre(
        @Query("genres") genre: Int,
        @Query("page_size") pageSize: Int = 5,
        @Query("ordering") ordering: String = "-rating"
    ): ResponseData<List<GamesItem>>

    @GET("platforms")
    suspend fun getPlatforms(
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
    ): ResponseData<List<PlatformsItem>>

    @GET("games")
    suspend fun getGamesBanner(
        @Query("ordering") ordering: String = "-relevance",
        @Query("date") date: String = "2023-10-01,2024-10-31",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 5,
    ): ResponseData<List<GamesItem>>

    @GET("games/lists/main")
    suspend fun getGamesDiscover(
        @Query("ordering") ordering: String = "-relevance",
        @Query("page") page: Int = 1,
        @Query("page_size") pageSize: Int = 10,
    ): ResponseData<List<GamesItem>>
}