package com.doanda.easymeal.ui.setting

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.ShoppingRepository
import com.doanda.easymeal.data.repository.UserRepository
import kotlinx.coroutines.launch

class SettingViewModel(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val shoppingRepository: ShoppingRepository,
) : ViewModel() {

    fun getUser() = userRepository.getUser()

    fun updateName(userId: Int, userName : String) = userRepository.updateName(userId, userName)

    fun setUserName(name: String) {
        viewModelScope.launch {
            userRepository.setUserName(name)
        }
    }

    fun logout() {
        viewModelScope.launch {
            userRepository.logout()
        }
    }

    fun getLoginStatus() = userRepository.getLoginStatus()
    fun getUserName() = userRepository.getUserName()
    fun clearPantry() {
        viewModelScope.launch {
            ingredientRepository.clearPantry()
        }
    }
    fun clearRecipes() {
        viewModelScope.launch {
            recipeRepository.clearRecipes()
        }
    }
    fun clearShoppingList() {
        viewModelScope.launch {
            shoppingRepository.clearShoppingList()
        }
    }
}
