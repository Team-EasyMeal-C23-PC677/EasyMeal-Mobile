package com.doanda.easymeal.data.source.model

data class RecipeEntity(
    val id: Int? = null,
    val title: String? = null,
    val description: String? = null,
    val totalTime: Int? = null,
    val serving: Int? = null,
    val imgUrl: String? = null,
    val missing: List<String?>? = null,
)

