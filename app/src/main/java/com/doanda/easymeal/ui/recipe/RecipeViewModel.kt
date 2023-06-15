package com.doanda.easymeal.ui.recipe

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.UserRepository

class RecipeViewModel(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository
) : ViewModel() {

    private val _recipeFilter = MutableLiveData<RecipeFragment.RecipeFilter?>()
    val recipeFilter: LiveData<RecipeFragment.RecipeFilter?> = _recipeFilter

    init {
        _recipeFilter.value = RecipeFragment.RecipeFilter()
    }

    fun setFilterTime(time: Int?) {
        val recipeFilter = _recipeFilter.value
        recipeFilter?.maxTotalTime = time
        _recipeFilter.value = recipeFilter
    }
    fun setFilterFavorite(isFavorite: Boolean?) {
        val recipeFilter = _recipeFilter.value
        recipeFilter?.isFavorite = isFavorite
        _recipeFilter.value = recipeFilter
    }
    fun setFilterServing(serving: IntRange?) {
        val recipeFilter = _recipeFilter.value
        recipeFilter?.servings = serving
        _recipeFilter.value = recipeFilter
    }
    fun setFilterSearch(query: String?) {
        val recipeFilter = _recipeFilter.value
        recipeFilter?.query = query
        _recipeFilter.value = recipeFilter
    }

    fun getUser() = userRepository.getUser()

    fun getRecommendedRecipes(userId: Int) =
        recipeRepository.getRecommendedRecipes(userId)

    fun getRecommendedRecipesLocal() =
        recipeRepository.getRecommendedRecipesLocal()

    fun addFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.addFavoriteRecipe(userId, recipeId)

    fun deleteFavoriteRecipe(userId: Int, recipeId: Int) =
        recipeRepository.deleteFavoriteRecipe(userId, recipeId)

    fun getPantryIngredientsLocal() = ingredientRepository.getPantryIngredientsLocal()
}