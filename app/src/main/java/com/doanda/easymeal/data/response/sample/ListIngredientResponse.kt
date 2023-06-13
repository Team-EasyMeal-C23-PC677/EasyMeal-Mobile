package com.doanda.easymeal.data.response.sample

import com.google.gson.annotations.SerializedName

data class ListIngredientResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("listIngredient")
	val listIngredient: List<ListIngredientItem>
)

data class ListIngredientItem(

	@field:SerializedName("ingId")
	val ingId: Int,

	@field:SerializedName("categoryName")
	val categoryName: String,

	@field:SerializedName("ingName")
	val ingName: String
)
