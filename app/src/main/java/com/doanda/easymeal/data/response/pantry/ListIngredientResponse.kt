package com.doanda.easymeal.data.response.pantry

import com.google.gson.annotations.SerializedName

data class ListIngredientResponse(

	@field:SerializedName("error")
	val error: String? = null,

	@field:SerializedName("message")
	val message: String? = null,

	@field:SerializedName("listIngredient")
	val listIngredient: List<ListIngredientItem?>? = null
)