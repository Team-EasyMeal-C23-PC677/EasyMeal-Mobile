package com.doanda.easymeal.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.ShoppingRepository
import com.doanda.easymeal.data.repository.UserRepository
import com.doanda.easymeal.data.source.model.UserEntity
import kotlinx.coroutines.launch

class LoginViewModel(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModel() {
    fun login(email: String, password: String) =
        userRepository.login(email, password)

    fun saveUser(user: UserEntity) {
        viewModelScope.launch {
            userRepository.saveUser(user)
        }
    }

    fun getFavoriteRecipes(userId: Int) = recipeRepository.getFavoriteRecipes(userId)

    fun getPantryIngredients(userId: Int) = ingredientRepository.getPantryIngredients(userId)

    fun getShoppingList(userId: Int) = shoppingRepository.getShoppingList(userId)

    fun getFirstTimeStatus() = userRepository.getFirstTimeStatus()

    fun setLoginStatus(isLogin: Boolean) {
        viewModelScope.launch {
            userRepository.setLoginStatus(isLogin)
        }
    }

    fun setUserName(userName: String) {
        viewModelScope.launch {
            userRepository.setUserName(userName)
        }
    }
}
