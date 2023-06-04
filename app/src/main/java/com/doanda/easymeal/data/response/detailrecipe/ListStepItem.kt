package com.doanda.easymeal.data.response.detailrecipe

import com.google.gson.annotations.SerializedName

data class ListStepItem(

	@field:SerializedName("no")
	val no: Int? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)