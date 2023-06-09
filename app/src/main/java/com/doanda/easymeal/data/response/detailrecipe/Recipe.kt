package com.doanda.easymeal.data.response.detailrecipe

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(

    @field:SerializedName("imgUrl")
	val imgUrl: String? = null,

    @field:SerializedName("totalTime")
	val totalTime: Int? = null,

    @field:SerializedName("description")
	val description: String? = null,

    @field:SerializedName("id")
	val id: Int? = null,

    @field:SerializedName("title")
	val title: String? = null,

    @field:SerializedName("listIngredient")
	val listIngredient: List<ListDetailIngredientItem?>? = null,

    @field:SerializedName("listStep")
	val listStep: List<ListStepItem?>? = null,

    @field:SerializedName("serving")
	val serving: Int? = null
) : Parcelable