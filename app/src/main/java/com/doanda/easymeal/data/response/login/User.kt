package com.doanda.easymeal.data.response.login

import com.google.gson.annotations.SerializedName

data class User(

	@field:SerializedName("userPassword")
	val userPassword: String,

	@field:SerializedName("userEmail")
	val userEmail: String,

	@field:SerializedName("userName")
	val userName: String,

	@field:SerializedName("userId")
	val userId: Int
)