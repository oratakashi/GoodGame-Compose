package com.oratakashi.goodgame.data.reqres

import com.oratakashi.goodgame.data.reqres.model.platforms.PlatformsItem
import com.oratakashi.goodgame.data.reqres.web.RawgApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class PlatformDataStore(
    private val web: RawgApi
): PlatformRepository {
    override fun getPlatform(): Flow<List<PlatformsItem>> {
        return flow {
            emit(web.getPlatforms().results ?: emptyList())
        }
    }
}