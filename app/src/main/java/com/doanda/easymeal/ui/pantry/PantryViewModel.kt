package com.doanda.easymeal.ui.pantry

import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.UserRepository

class PantryViewModel (
    private val userRepository: UserRepository,
    private val ingredientRepository: IngredientRepository,
) : ViewModel() {

//    fun getIngredients() =
//        ingredientRepository.getIngredients()
//
//    fun getUserIngredients() =
//        ingredientRepository.getUserIngredients()
//
//    fun getLoginStatus() =
//        userRepository.getLoginStatus()


}