package com.oratakashi.goodgame.data.reqres.model.platforms

import com.google.gson.annotations.SerializedName
import com.oratakashi.goodgame.domain.model.platforms.Platforms

data class PlatformsItem(

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
) {
	fun toPlatforms(): Platforms {
		return Platforms(
			name.orEmpty(),
			id ?: 1
		)
	}
}