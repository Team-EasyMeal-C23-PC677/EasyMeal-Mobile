package com.doanda.easymeal.data.response.shoppinglist

import com.google.gson.annotations.SerializedName

data class ShoppingListItem(

	@field:SerializedName("unit")
	val unit: String? = null,

	@field:SerializedName("qty")
	val qty: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("ingName")
	val ingName: String? = null
)