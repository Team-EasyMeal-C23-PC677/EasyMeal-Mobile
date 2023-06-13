package com.doanda.easymeal.data.response.recipe

import com.google.gson.annotations.SerializedName

data class ListRecipeResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("listRecipe")
	val listRecipe: List<ListRecipeItem> = emptyList()
)