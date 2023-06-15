package com.doanda.easymeal.utils

import com.doanda.easymeal.data.response.detailrecipe.Recipe

fun defaultRecipe() = Recipe(
    imgUrl = "",
    totalTime = 0,
    description = "",
    id = -1,
    title = "",
    listIngredient = emptyList(),
    listStep = emptyList(),
    serving = 0,
)