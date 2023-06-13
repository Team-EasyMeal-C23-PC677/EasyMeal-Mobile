package com.doanda.easymeal.data.source.model

data class DetailIngredientEntity(
    val id: Int,
    val ingName: String,
    val qty: Float,
    val unit: String,
    var isInPantry: Boolean = false,
    var isInShoppingList: Boolean = false,
)
