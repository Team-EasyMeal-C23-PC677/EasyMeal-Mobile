package com.doanda.easymeal.ui.recipedetail

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.ShoppingRepository
import com.doanda.easymeal.data.repository.UserRepository

class RecipeDetailViewModel(
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository,
    private val recipeRepository: RecipeRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {

    fun getUser() = userRepository.getUser()

    fun getDetailRecipeById(recipeId: Int) = recipeRepository.getDetailRecipeById(recipeId)
    fun getLoginStatus() = userRepository.getLoginStatus()
}