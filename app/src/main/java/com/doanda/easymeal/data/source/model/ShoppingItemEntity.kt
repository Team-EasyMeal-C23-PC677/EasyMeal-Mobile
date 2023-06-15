package com.doanda.easymeal.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "shoppingItem")
data class ShoppingItemEntity (
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "ingName")
    val ingName: String,

    @field:ColumnInfo(name = "qty")
    var qty: Float,

    @field:ColumnInfo(name = "unit")
    var unit: String,

    @field:ColumnInfo(name = "isHave")
    var isHave: Boolean
)