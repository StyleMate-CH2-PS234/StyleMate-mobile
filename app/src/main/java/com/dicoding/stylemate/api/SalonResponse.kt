package com.dicoding.stylemate.api

import com.google.gson.annotations.SerializedName

data class SalonResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("list salon")
	val listPotong: List<ListPotongItem?>? = null
)

data class ListPotongItem(

	@field:SerializedName("imageUrls")
	val imageUrls: List<String?>? = null,

	@field:SerializedName("name")
	val name: String? = null,

	@field:SerializedName("rating")
	val rating: String? = null,

	@field:SerializedName("geometry")
	val geometry: Geometry? = null,

	@field:SerializedName("formatted_phone_number")
	val formattedPhoneNumber: String? = null,

	@field:SerializedName("url")
	val url: String? = null
)

data class Location(

	@field:SerializedName("lng")
	val lng: Double? = null,

	@field:SerializedName("lat")
	val lat: Double? = null
)

data class Geometry(

	@field:SerializedName("location")
	val location: Location? = null
)
