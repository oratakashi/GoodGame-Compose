package com.oratakashi.goodgame.data.reqres.model.genre

import com.google.gson.annotations.SerializedName
import com.oratakashi.goodgame.domain.model.genre.Genre

data class GenreItem(

	@field:SerializedName("games_count")
	val gamesCount: Int? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("image_background")
	val imageBackground: String? = null,

	@field:SerializedName("slug")
	val slug: String? = null
) {
	fun toGenre(): Genre {
		return Genre(
			gamesCount ?: 0,
			name.orEmpty(),
			id ?: 0,
			imageBackground.orEmpty(),
			slug.orEmpty()
		)
	}
}