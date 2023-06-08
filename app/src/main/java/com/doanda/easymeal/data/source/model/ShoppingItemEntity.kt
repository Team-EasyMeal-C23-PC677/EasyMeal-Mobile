package com.doanda.easymeal.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "shoppingItem")
class ShoppingItemEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "ingName")
    val ingName: String,

    @field:ColumnInfo(name = "qty")
    val qty: Float? = null,

    @field:ColumnInfo(name = "unit")
    val unit: String? = null,

    @field:ColumnInfo(name = "isHave")
    var isHave: Boolean
)