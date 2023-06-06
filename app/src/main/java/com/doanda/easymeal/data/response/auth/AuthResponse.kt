package com.doanda.easymeal.data.response.auth

import com.google.gson.annotations.SerializedName

data class AuthResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("user")
	val user: User? = null
)