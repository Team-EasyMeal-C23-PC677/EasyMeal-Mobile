package com.doanda.easymeal.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.UserRepository
import com.doanda.easymeal.data.source.model.IngredientEntity

class RecipeViewModel(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    private var _listPantryIng = MutableLiveData<List<IngredientEntity>>()
    val listPantryIng: LiveData<List<IngredientEntity>> = _listPantryIng
//    ingredientRepository.getPantryIngredientsLocal() // = _listPantryIng

    fun getUser() = userRepository.getUser()

    fun setPantryIngs(listIng: List<IngredientEntity>) {
        _listPantryIng.value = listIng
    }

    fun getRecommendedRecipes(userId: Int) =
        recipeRepository.getRecommendedRecipes(userId)

    fun getRecommendedRecipesLocal() =
        recipeRepository.getRecommendedRecipesLocal()

    fun addFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.addFavoriteRecipe(userId, recipeId)

    fun deleteFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.deleteFavoriteRecipe(userId, recipeId)

    fun getPantryIngredientsLocal() = ingredientRepository.getPantryIngredientsLocal()

    fun isPantryNotEmpty() =
        ingredientRepository.isPantryNotEmpty()

    fun getLoginStatus() = userRepository.getLoginStatus()
    fun getFavoriteRecipesLocal() = recipeRepository.getFavoriteRecipesLocal()

}