package com.doanda.easymeal.ui.pantry

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.UserRepository

class PantryViewModel (
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository,
) : ViewModel() {
    fun getUser() =
        userRepository.getUser()

    fun getAllIngredients() =
        ingredientRepository.getAllIngredients()

    fun getPantryIngredients(userId: Int) =
        ingredientRepository.getPantryIngredients(userId)

    fun addPantryIngredient(userId: Int, ingId: Int) =
        ingredientRepository.addPantryIngredient(userId, ingId)

    fun deletePantryIngredient(userId: Int, ingId: Int) =
        ingredientRepository.deletePantryIngredient(userId, ingId)

    fun getAllIngredientsLocal() =
        ingredientRepository.getAllIngredientsLocal()

    fun getPantryIngredientsLocal() =
        ingredientRepository.getPantryIngredientsLocal()

    fun getIngredientsByCategoryLocal(categoryName: String) =
        ingredientRepository.getIngredientsByCategoryLocal(categoryName)

    fun isPantryNotEmpty() = ingredientRepository.isPantryNotEmpty()

    fun getLoginStatus() = userRepository.getLoginStatus()

}