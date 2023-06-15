package com.doanda.easymeal.data.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.ShoppingRepository
import com.doanda.easymeal.data.repository.UserRepository
import com.doanda.easymeal.data.source.database.IngredientDatabase
import com.doanda.easymeal.data.source.database.RecipeDatabase
import com.doanda.easymeal.data.source.database.ShoppingDatabase
import com.doanda.easymeal.data.source.local.UserPreferences
import com.doanda.easymeal.data.source.remote.ApiConfig

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user")
object Injection {

    fun provideUserRepository(context: Context): UserRepository {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()

        return UserRepository.getInstance(
            userPreferences = userPreferences,
            apiService = apiService,
        )
    }

    fun provideRecipeRepository(context: Context): RecipeRepository {
        val recipeDatabase = RecipeDatabase.getInstance(context)
        val recipeDao = recipeDatabase.recipeDao()
        val apiService = ApiConfig.getApiService()

        return RecipeRepository.getInstance(
            apiService = apiService,
            recipeDao = recipeDao
        )
    }
    fun provideIngredientRepository(context: Context): IngredientRepository {
        val ingredientDatabase = IngredientDatabase.getInstance(context)
        val ingredientDao = ingredientDatabase.ingredientDao()
        val shoppingDatabase = ShoppingDatabase.getInstance(context)
        val shoppingDao = shoppingDatabase.shoppingDao()
        val apiService = ApiConfig.getApiService()

        return IngredientRepository.getInstance(
            apiService = apiService,
            ingredientDao = ingredientDao,
            shoppingDao = shoppingDao,
        )
    }
    fun provideShoppingRepository(context: Context): ShoppingRepository {
        val shoppingDatabase = ShoppingDatabase.getInstance(context)
        val shoppingDao = shoppingDatabase.shoppingDao()
        val apiService = ApiConfig.getApiService()

        return ShoppingRepository.getInstance(
            apiService = apiService,
            shoppingDao = shoppingDao
        )
    }}