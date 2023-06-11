package com.doanda.easymeal.data.source.model

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "detection")
data class DetectionEntity(
    @field:ColumnInfo(name = "id")
    @field:PrimaryKey
    val ingId: Int,

    @field:ColumnInfo(name = "categoryName")
    val categoryName: String,

    @field:ColumnInfo(name = "ingName")
    val ingName: String,

    @field:ColumnInfo(name = "isHave")
    var isHave: Boolean? = false,

    @field:ColumnInfo(name = "confidence")
    var confidence: Float? = 0.0f,
)
