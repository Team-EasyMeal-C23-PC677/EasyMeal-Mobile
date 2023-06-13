package com.doanda.easymeal.data.response.sample

import com.google.gson.annotations.SerializedName

data class DetailRecipeResponse(

	@field:SerializedName("recipe")
	val recipe: Recipe
)

data class ListStepItem(

	@field:SerializedName("no")
	val no: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
)

data class Recipe(

	@field:SerializedName("imgUrl")
	val imgUrl: String,

	@field:SerializedName("totalTime")
	val totalTime: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("listIngredient")
	val listIngredient: List<ListIngredientItem>,

	@field:SerializedName("listStep")
	val listStep: List<ListStepItem>,

	@field:SerializedName("serving")
	val serving: Int
)
