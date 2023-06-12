package com.doanda.easymeal.data.response.detailrecipe

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
data class Recipe(

    @field:SerializedName("imgUrl")
	val imgUrl: String,

    @field:SerializedName("totalTime")
	val totalTime: Int,

    @field:SerializedName("description")
	val description: String,

    @field:SerializedName("id")
	val id: Int,

    @field:SerializedName("title")
	val title: String,

    @field:SerializedName("listIngredient")
	val listIngredient: List<ListDetailIngredientItem>,

    @field:SerializedName("listStep")
	val listStep: List<ListStepItem>,

    @field:SerializedName("serving")
	val serving: Int
) : Parcelable