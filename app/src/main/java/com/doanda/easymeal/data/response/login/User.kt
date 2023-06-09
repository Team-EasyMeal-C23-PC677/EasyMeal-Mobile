package com.doanda.easymeal.data.response.login

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("userPassword")
	val userPassword: String? = null,

	@field:SerializedName("userEmail")
	val userEmail: String? = null,

	@field:SerializedName("userName")
	val userName: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)