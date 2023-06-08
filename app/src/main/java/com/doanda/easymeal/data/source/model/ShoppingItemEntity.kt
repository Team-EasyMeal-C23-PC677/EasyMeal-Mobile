package com.doanda.easymeal.data.source.model

import com.google.gson.annotations.SerializedName

data class ShoppingItemEntity (
    val id: Int,
    val ingName: String,
    val qty: Float,
    val unit: String,
)