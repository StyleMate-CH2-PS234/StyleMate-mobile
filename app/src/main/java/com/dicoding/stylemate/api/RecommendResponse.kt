package com.dicoding.stylemate.api

import com.google.gson.annotations.SerializedName

data class RecommendResponse(

	@field:SerializedName("imageUrls")
	val imageUrls: List<String?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null
)
