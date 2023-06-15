package com.doanda.easymeal.ui

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.doanda.easymeal.data.di.Injection
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.ShoppingRepository
import com.doanda.easymeal.data.repository.UserRepository
import com.doanda.easymeal.ui.detection.result.DetectionResultViewModel
import com.doanda.easymeal.ui.favorite.FavoriteViewModel
import com.doanda.easymeal.ui.login.LoginViewModel
import com.doanda.easymeal.ui.main.MainViewModel
import com.doanda.easymeal.ui.pantry.PantryViewModel
import com.doanda.easymeal.ui.recipe.RecipeViewModel
import com.doanda.easymeal.ui.recipedetail.DetailIngredientViewModel
import com.doanda.easymeal.ui.recipedetail.RecipeDetailViewModel
import com.doanda.easymeal.ui.register.RegisterViewModel
import com.doanda.easymeal.ui.setting.SettingViewModel
import com.doanda.easymeal.ui.shoppinglist.ShoppingListViewModel
import com.doanda.easymeal.ui.welcome.WelcomeViewModel

class ViewModelFactory(
    private val userRepository: UserRepository,
    private val recipeRepository: RecipeRepository,
    private val ingredientRepository: IngredientRepository,
    private val shoppingRepository: ShoppingRepository
) : ViewModelProvider.NewInstanceFactory() {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(LoginViewModel::class.java) ->
                LoginViewModel(userRepository, recipeRepository, ingredientRepository, shoppingRepository) as T
            modelClass.isAssignableFrom(RegisterViewModel::class.java) ->
                RegisterViewModel(userRepository) as T
            modelClass.isAssignableFrom(MainViewModel::class.java) ->
                MainViewModel(userRepository) as T
            modelClass.isAssignableFrom(SettingViewModel::class.java) ->
                SettingViewModel(userRepository, recipeRepository, ingredientRepository, shoppingRepository) as T
            modelClass.isAssignableFrom(PantryViewModel::class.java) ->
                PantryViewModel(userRepository, ingredientRepository) as T
            modelClass.isAssignableFrom(RecipeViewModel::class.java) ->
                RecipeViewModel(userRepository, recipeRepository, ingredientRepository) as T
            modelClass.isAssignableFrom(RecipeDetailViewModel::class.java) ->
                RecipeDetailViewModel(userRepository, recipeRepository, ingredientRepository) as T
            modelClass.isAssignableFrom(DetailIngredientViewModel::class.java) ->
                DetailIngredientViewModel(ingredientRepository, shoppingRepository) as T
            modelClass.isAssignableFrom(FavoriteViewModel::class.java) ->
                FavoriteViewModel(userRepository, recipeRepository) as T
            modelClass.isAssignableFrom(ShoppingListViewModel::class.java) ->
                ShoppingListViewModel(userRepository, shoppingRepository) as T
            modelClass.isAssignableFrom(DetectionResultViewModel::class.java) ->
                DetectionResultViewModel(userRepository, ingredientRepository) as T
            modelClass.isAssignableFrom(WelcomeViewModel::class.java) ->
                WelcomeViewModel(userRepository, ingredientRepository) as T

            else -> throw IllegalArgumentException("Unknown ViewModel class " + modelClass.name)
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: ViewModelFactory? = null
        fun getInstance(context: Context): ViewModelFactory {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: ViewModelFactory(
                    Injection.provideUserRepository(context),
                    Injection.provideRecipeRepository(context),
                    Injection.provideIngredientRepository(context),
                    Injection.provideShoppingRepository(context),
                )
            }.also { INSTANCE = it }
        }
    }

}