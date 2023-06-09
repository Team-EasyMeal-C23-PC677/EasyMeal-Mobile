package com.doanda.easymeal.data.response.detailrecipe

import com.google.gson.annotations.SerializedName

data class DetailRecipeResponse(

	@field:SerializedName("recipe")
	val recipe: Recipe,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)