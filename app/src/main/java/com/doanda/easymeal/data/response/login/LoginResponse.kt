package com.doanda.easymeal.data.response.login

import com.doanda.easymeal.utils.defaultUser
import com.google.gson.annotations.SerializedName

data class LoginResponse(

	@field:SerializedName("error")
	val error: Boolean = false,

	@field:SerializedName("message")
	val message: String = "success",

	@field:SerializedName("user")
	val user: User = defaultUser()
)