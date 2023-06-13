package com.doanda.easymeal.data.response.favorite

import com.google.gson.annotations.SerializedName

data class ListFavoriteRecipeItem(

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