package com.doanda.easymeal.ui.favorite

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.UserRepository

class FavoriteViewModel(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository
) : ViewModel() {
    fun getUser() = userRepository.getUser()

    fun getFavoriteRecipes(userId: Int) =
        recipeRepository.getFavoriteRecipes(userId)

    fun addFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.addFavoriteRecipe(userId, recipeId)

    fun deleteFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.deleteFavoriteRecipe(userId, recipeId)

    fun getFavoriteRecipesLocal() =
        recipeRepository.getFavoriteRecipesLocal()
}