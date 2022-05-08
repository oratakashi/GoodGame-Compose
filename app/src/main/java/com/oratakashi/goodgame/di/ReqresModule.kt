package com.oratakashi.goodgame.di

import com.oratakashi.goodgame.data.reqres.GenreDataStore
import com.oratakashi.goodgame.data.reqres.GenreRepository
import com.oratakashi.goodgame.data.reqres.PlatformDataStore
import com.oratakashi.goodgame.data.reqres.PlatformRepository
import com.oratakashi.goodgame.data.reqres.paging.GenrePaging
import com.oratakashi.goodgame.data.reqres.web.RawgApi
import com.oratakashi.goodgame.domain.GenreInteractor
import com.oratakashi.goodgame.domain.GenreUsecase
import com.oratakashi.goodgame.domain.PlatformUsecase
import com.oratakashi.goodgame.domain.PlatfromInteractor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class ReqresModule {
    @Provides
    @Singleton
    fun provideGenreRepository(
        web: RawgApi
    ): GenreRepository = GenreDataStore(web)

    @Provides
    @Singleton
    fun provideGenreUsecase(
        repo: GenreRepository
    ): GenreUsecase = GenreInteractor(repo)

    @Provides
    @Singleton
    fun providePlatformRepository(
        web: RawgApi
    ): PlatformRepository = PlatformDataStore(web)

    @Provides
    @Singleton
    fun providePlatformUsecase(
        repo: PlatformRepository
    ): PlatformUsecase = PlatfromInteractor(repo)
}