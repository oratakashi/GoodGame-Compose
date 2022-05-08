package com.oratakashi.goodgame.data.reqres.model.games

import com.google.gson.annotations.SerializedName
import com.oratakashi.goodgame.domain.model.games.Games

data class GamesItem(

	@field:SerializedName("background_image")
	val backgroundImage: String? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("released")
	val released: String? = null
) {
	fun toGames(): Games {
		return Games(
			backgroundImage.orEmpty(),
			name.orEmpty(),
			id ?: 0,
			released.orEmpty()
		)
	}
}