package com.dicoding.stylemate.api

import com.google.gson.annotations.SerializedName

data class UploadResponse(

	@field:SerializedName("classId")
	val classId: Int? = null,

	@field:SerializedName("predictionText")
	val predictionText: String? = null
)
