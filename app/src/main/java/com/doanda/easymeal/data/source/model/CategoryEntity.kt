package com.doanda.easymeal.data.source.model

import com.doanda.easymeal.data.response.pantry.ListIngredientItem

data class CategoryEntity(

    val categoryName: String? = null,
    var isExpandable: Boolean = false,
    val listIngredient: List<ListIngredientItem>
)