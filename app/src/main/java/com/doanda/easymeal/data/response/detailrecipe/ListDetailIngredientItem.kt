package com.doanda.easymeal.data.response.detailrecipe

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListDetailIngredientItem(

	@field:SerializedName("unit")
	val unit: String,

	@field:SerializedName("qty")
	val qty: Float,

	@field:SerializedName("id")
	val id: Int,

	@field:SerializedName("name")
	val name: String
) : Parcelable