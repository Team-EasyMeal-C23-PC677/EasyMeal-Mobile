package com.doanda.easymeal.data.response.sample

import com.google.gson.annotations.SerializedName

data class ListRecipeResponse(

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("listRecipe")
	val listRecipe: List<ListRecipeItem>
)

data class ListRecipeItem(

	@field:SerializedName("img_url")
	val imgUrl: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("total_time")
	val totalTime: Int,

	@field:SerializedName("serving")
	val serving: Int
)
