package com.doanda.easymeal.utils

import com.doanda.easymeal.data.response.detailrecipe.Recipe
import com.doanda.easymeal.data.response.login.User

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

fun defaultUser() = User(
    userId = -1,
    userName = "",
    userEmail = "",
    userPassword = ""
)