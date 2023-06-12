package com.doanda.easymeal.data.response.detailrecipe

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class ListStepItem(

	@field:SerializedName("no")
	val no: Int,

	@field:SerializedName("description")
	val description: String,

	@field:SerializedName("id")
	val id: Int
) : Parcelable