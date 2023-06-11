package com.doanda.easymeal.ui.detection.result

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.UserRepository

class DetectionResultViewModel(
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {
    fun getUser() = userRepository.getUser()

    fun getLoginStatus() = userRepository.getLoginStatus()

    fun searchIngredientByName(ingName: String)  = ingredientRepository.searchIngredientByName(ingName)

    fun addPantryIngredient(userId: Int, ingId: Int) = ingredientRepository.addPantryIngredient(userId, ingId)
}
