package com.doanda.easymeal.data.response.recipe

import com.google.gson.annotations.SerializedName

data class ListRecipeResponse(

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("listRecipe")
	val listRecipe: List<ListRecipeItem?>? = null
)