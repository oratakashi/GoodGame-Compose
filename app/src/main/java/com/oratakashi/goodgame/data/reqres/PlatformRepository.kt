package com.oratakashi.goodgame.data.reqres

import com.oratakashi.goodgame.data.reqres.model.platforms.PlatformsItem
import kotlinx.coroutines.flow.Flow

interface PlatformRepository {
    fun getPlatform(): Flow<List<PlatformsItem>>
}