package com.doanda.easymeal.data.response.detailrecipe

import com.doanda.easymeal.utils.defaultRecipe
import com.google.gson.annotations.SerializedName

data class DetailRecipeResponse(
	@field:SerializedName("error")
	val error: Boolean = false,

	@field:SerializedName("message")
	val message: String = "success",

	@field:SerializedName("recipe")
	val recipe: Recipe = defaultRecipe(),
)