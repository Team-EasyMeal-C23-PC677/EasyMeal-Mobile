package com.doanda.easymeal.ui.recipe

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.UserRepository

class RecipeViewModel(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
) : ViewModel() {
    fun getUser() = userRepository.getUser()

    fun getAllRecipes() =
        recipeRepository.getAllRecipes()

    fun getFavoriteRecipes(userId: Int) =
        recipeRepository.getFavoriteRecipes(userId)

    fun getRecommendedRecipes(userId: Int) =
        recipeRepository.getRecommendedRecipes(userId)

    fun addFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.addFavoriteRecipe(userId, recipeId)

    fun deleteFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.deleteFavoriteRecipe(userId, recipeId)

    fun getRecommendedRecipesLocal() =
        recipeRepository.getRecommendedRecipesLocal()
}