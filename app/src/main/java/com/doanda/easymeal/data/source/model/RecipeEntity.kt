package com.doanda.easymeal.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipe")
class RecipeEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val id: Int,

    @field:ColumnInfo(name = "title")
    val title: String? = null,

    @field:ColumnInfo(name = "description")
    val description: String? = null,

    @field:ColumnInfo(name = "totalTime")
    val totalTime: Int? = null,

    @field:ColumnInfo(name = "serving")
    val serving: Int? = null,

    @field:ColumnInfo(name = "imgUrl")
    val imgUrl: String? = null,

    @field:ColumnInfo(name = "isBookmarked")
    var isFavorite: Boolean,

    @field:ColumnInfo(name = "isRecommended")
    var isRecommended: Boolean,
)

