package com.doanda.easymeal.data.response.detailrecipe

import com.google.gson.annotations.SerializedName

data class ListDetailIngredientItem(

	@field:SerializedName("unit")
	val unit: String? = null,

	@field:SerializedName("qty")
	val qty: Double? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("ingName")
	val ingName: String? = null,

	@field:SerializedName("name")
	val name: String? = null
)