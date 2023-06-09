package com.doanda.easymeal.data.response.detailrecipe

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListDetailIngredientItem(

	@field:SerializedName("unit")
	val unit: String? = null,

	@field:SerializedName("qty")
	val qty: Float? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("name")
	val name: String? = null
) : Parcelable