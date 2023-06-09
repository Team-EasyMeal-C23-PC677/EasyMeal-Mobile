package com.doanda.easymeal.data.response.shoppinglist

import com.google.gson.annotations.SerializedName

data class ShoppingListResponse(

	@field:SerializedName("shoppingList")
	val shoppingList: List<ShoppingListItem?>? = null,

	@field:SerializedName("error")
	val error: Boolean? = null,

	@field:SerializedName("message")
	val message: String? = null
)