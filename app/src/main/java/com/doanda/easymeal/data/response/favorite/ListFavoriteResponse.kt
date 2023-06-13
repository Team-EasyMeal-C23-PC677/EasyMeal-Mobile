package com.doanda.easymeal.data.response.favorite

import com.google.gson.annotations.SerializedName

data class ListFavoriteResponse(

	@field:SerializedName("listFavoriteRecipe")
	val listFavoriteRecipe: List<ListFavoriteRecipeItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)