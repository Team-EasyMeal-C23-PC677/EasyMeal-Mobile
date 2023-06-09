package com.doanda.easymeal.di

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import com.doanda.easymeal.data.repository.IngredientRepository
import com.doanda.easymeal.data.repository.RecipeRepository
import com.doanda.easymeal.data.repository.ShoppingRepository
import com.doanda.easymeal.data.repository.UserRepository
import com.doanda.easymeal.data.source.local.UserPreferences
import com.doanda.easymeal.data.source.remote.ApiConfig
import com.doanda.easymeal.data.source.remote.DummyApiService

val Context.dataStore: DataStore<Preferences> by preferencesDataStore("user")
object Injection {

    fun provideUserRepository(context: Context): UserRepository {
        val userPreferences = UserPreferences.getInstance(context.dataStore)
        val apiService = ApiConfig.getApiService()
        val dummyApiService = DummyApiService()

        return UserRepository.getInstance(
            userPreferences = userPreferences,
            apiService = apiService,
            dummyApiService = dummyApiService,
        )
    }

    fun provideRecipeRepository(context: Context): RecipeRepository {
        val apiService = ApiConfig.getApiService()
        val dummyApiService = DummyApiService()

        return RecipeRepository.getInstance(
            apiService = apiService,
            dummyApiService = dummyApiService,
        )
    }
    fun provideIngredientRepository(context: Context): IngredientRepository {
        val apiService = ApiConfig.getApiService()
        val dummyApiService = DummyApiService()

        return IngredientRepository.getInstance(
            apiService = apiService,
            dummyApiService = dummyApiService,
        )
    }
    fun provideShoppingRepository(context: Context): ShoppingRepository {
        val apiService = ApiConfig.getApiService()
        val dummyApiService = DummyApiService()

        return ShoppingRepository.getInstance(
            apiService = apiService,
            dummyApiService = dummyApiService,
        )
    }}