package com.doanda.easymeal.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "ingredient")
class IngredientEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val ingId: String,

    @field:ColumnInfo(name = "categoryName")
    val categoryName: String? = null,

    @field:ColumnInfo(name = "ingName")
    val ingName: String,

    @field:ColumnInfo(name = "isHave")
    var isHave: Boolean
)
