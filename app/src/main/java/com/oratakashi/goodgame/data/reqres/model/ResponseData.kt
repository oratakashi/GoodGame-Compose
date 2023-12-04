package com.oratakashi.goodgame.data.reqres.model

import com.google.gson.annotations.SerializedName

data class ResponseData<T>(

    @field:SerializedName("next")
    val next: Any? = null,

    @field:SerializedName("previous")
    val previous: Any? = null,

    @field:SerializedName("count")
    val count: Int? = null,

    @field:SerializedName("results")
    val results: T? = null
)