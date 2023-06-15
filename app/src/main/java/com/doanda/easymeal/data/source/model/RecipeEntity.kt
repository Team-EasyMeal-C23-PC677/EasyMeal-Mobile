package com.doanda.easymeal.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
data class RecipeEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "title")
    val title: String,

    @field:ColumnInfo(name = "description")
    val description: String,

    @field:ColumnInfo(name = "totalTime")
    val totalTime: Int,

    @field:ColumnInfo(name = "serving")
    val serving: Int,

    @field:ColumnInfo(name = "imgUrl")
    val imgUrl: String,

    @field:ColumnInfo(name = "isFavorite")
    var isFavorite: Boolean,

    @field:ColumnInfo(name = "isRecommended")
    var isRecommended: Boolean,

    @field:ColumnInfo(name = "order")
    var order: Int? = 0,
)

