package com.doanda.easymeal.data.response.detailrecipe

import com.google.gson.annotations.SerializedName

data class Recipe(

	@field:SerializedName("imgUrl")
	val imgUrl: String? = null,

	@field:SerializedName("totalTime")
	val totalTime: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("listIngredient")
	val listIngredient: List<ListIngredientItem?>? = null,

	@field:SerializedName("listStep")
	val listStep: List<ListStepItem?>? = null,

	@field:SerializedName("serving")
	val serving: Int? = null
)