package com.doanda.easymeal.data.response.shoppinglist

import com.google.gson.annotations.SerializedName

data class ShoppingListItem(

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("qty")
	val qty: Float,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("ingName")
	val ingName: String
)