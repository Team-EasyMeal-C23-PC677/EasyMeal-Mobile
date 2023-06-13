package com.doanda.easymeal.ui.recipedetail

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.UserRepository

class RecipeDetailViewModel(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
) : ViewModel() {
    fun getUser() = userRepository.getUser()

    fun getIngredientsByIds(listId: List<Int>) =
        ingredientRepository.getIngredientsByIds(listId)

    fun isRecipeFavoriteLocal(recipeId: Int) =
        recipeRepository.isRecipeFavoriteLocal(recipeId)

    fun getDetailRecipeById(recipeId: Int) =
        recipeRepository.getDetailRecipeById(recipeId)

    fun addFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.addFavoriteRecipe(userId, recipeId)

    fun deleteFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.deleteFavoriteRecipe(userId, recipeId)
}