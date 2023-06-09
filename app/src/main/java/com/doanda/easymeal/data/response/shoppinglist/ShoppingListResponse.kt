package com.doanda.easymeal.data.response.shoppinglist

import com.google.gson.annotations.SerializedName

data class ShoppingListResponse(

	@field:SerializedName("shoppingList")
	val shoppingList: List<ShoppingListItem>,

	@field:SerializedName("error")
	val error: Boolean,

	@field:SerializedName("message")
	val message: String
)