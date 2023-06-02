package com.doanda.easymeal.data.response

import com.google.gson.annotations.SerializedName

data class ListRecipeItem(
	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("description")
	val description: String? = null,

	@field:SerializedName("total_time")
	val totalTime: Int? = null,

	@field:SerializedName("serving")
	val serving: Int? = null,

	@field:SerializedName("img_url")
	val imgUrl: String? = null,

	@field:SerializedName("missing")
	val missing: List<String?>? = null,
)
