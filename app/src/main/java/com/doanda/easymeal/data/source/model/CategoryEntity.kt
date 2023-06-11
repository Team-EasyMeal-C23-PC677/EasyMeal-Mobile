package com.doanda.easymeal.data.source.model

data class CategoryEntity(

    val categoryName: String,
    var isExpanded: Boolean = true,
    val listIngredient: List<IngredientEntity>
)