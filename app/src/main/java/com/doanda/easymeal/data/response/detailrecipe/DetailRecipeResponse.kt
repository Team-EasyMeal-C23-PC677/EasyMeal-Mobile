package com.doanda.easymeal.data.response.detailrecipe

import com.google.gson.annotations.SerializedName

data class DetailRecipeResponse(

	@field:SerializedName("recipe")
	val recipe: Recipe? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)