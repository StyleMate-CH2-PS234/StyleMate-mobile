package com.dicoding.stylemate.api

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

data class LoginResponse(

	@field:SerializedName("data")
	val data: Data? = null,

	@field:SerializedName("success")
	val success: Boolean? = null,

	@field:SerializedName("errors")
	val errors: Any? = null
)

@Parcelize
data class ProviderDataItem(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("photoURL")
	val photoURL: String? = null,

	@field:SerializedName("phoneNumber")
	val phoneNumber: String? = null,

	@field:SerializedName("providerId")
	val providerId: String? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable


@Parcelize
data class StsTokenManager(

	@field:SerializedName("expirationTime")
	val expirationTime: Long? = null,

	@field:SerializedName("accessToken")
	val accessToken: String? = null,

	@field:SerializedName("refreshToken")
	val refreshToken: String? = null
) : Parcelable

@Parcelize
data class Data(

	@field:SerializedName("uid")
	val uid: String? = null,

	@field:SerializedName("emailVerified")
	val emailVerified: Boolean? = null,

	@field:SerializedName("photoURL")
	val photoURL: String? = null,

	@field:SerializedName("createdAt")
	val createdAt: String? = null,

	@field:SerializedName("isAnonymous")
	val isAnonymous: Boolean? = null,

	@field:SerializedName("stsTokenManager")
	val stsTokenManager: StsTokenManager? = null,

	@field:SerializedName("lastLoginAt")
	val lastLoginAt: String? = null,

	@field:SerializedName("apiKey")
	val apiKey: String? = null,

	@field:SerializedName("providerData")
	val providerData: List<ProviderDataItem?>? = null,

	@field:SerializedName("displayName")
	val displayName: String? = null,

	@field:SerializedName("appName")
	val appName: String? = null,

	@field:SerializedName("email")
	val email: String? = null
) : Parcelable
