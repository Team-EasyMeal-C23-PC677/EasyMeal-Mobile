package com.doanda.easymeal.data.response.pantry

import com.google.gson.annotations.SerializedName

data class ListIngredientItem(

	@field:SerializedName("ingId")
	val ingId: Int,

	@field:SerializedName("categoryName")
	val categoryName: String,

	@field:SerializedName("ingName")
	val ingName: String
)