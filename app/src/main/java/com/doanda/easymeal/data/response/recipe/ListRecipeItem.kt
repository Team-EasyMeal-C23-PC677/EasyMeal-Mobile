package com.doanda.easymeal.data.response.recipe

import com.google.gson.annotations.SerializedName

data class ListRecipeItem(
	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("title")
	val title: String,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("total_time")
	val totalTime: Int,

	@field:SerializedName("serving")
	val serving: Int,

	@field:SerializedName("img_url")
	val imgUrl: String,
)
