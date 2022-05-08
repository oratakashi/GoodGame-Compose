package com.oratakashi.goodgame.domain

import com.oratakashi.goodgame.data.reqres.PlatformRepository
import com.oratakashi.goodgame.domain.model.platforms.Platforms
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class PlatfromInteractor @Inject constructor(
    private val repo: PlatformRepository
): PlatformUsecase {
    override fun getPlatform(): Flow<List<Platforms>> {
        return repo.getPlatform().map { it.map { data -> data.toPlatforms() } }
    }
}