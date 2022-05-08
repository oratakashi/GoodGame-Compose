package com.oratakashi.goodgame.domain

import com.oratakashi.goodgame.domain.model.platforms.Platforms
import kotlinx.coroutines.flow.Flow

interface PlatformUsecase {
    fun getPlatform(): Flow<List<Platforms>>
}