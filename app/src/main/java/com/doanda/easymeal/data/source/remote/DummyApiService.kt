package com.doanda.easymeal.data.source.remote

import com.doanda.easymeal.data.response.auth.AuthResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
import com.doanda.easymeal.data.response.pantry.ListIngredientResponse
import com.doanda.easymeal.data.response.recipe.ListRecipeResponse
import com.doanda.easymeal.utils.*

class DummyApiService(
//    private val context: Context
) {

    suspend fun register(
        name: String,
        email: String,
        password: String,
    ): AuthResponse {
        return loadFromJsonAuthResponse(Application.appContext)
    }

    suspend fun login(
        email: String,
        password: String,
    ): AuthResponse {
        return loadFromJsonAuthResponse(Application.appContext)
    }

    suspend fun getAllRecipes(): ListRecipeResponse {
        return loadFromJsonListRecipeResponse(Application.appContext)
    }

    suspend fun getRecommendedRecipes(
        userId: String,
    ): ListRecipeResponse {
        return loadFromJsonListRecommendedRecipeResponse(Application.appContext)
    }

    suspend fun getFavoriteRecipes(
        userId: String
    ): ListRecipeResponse {
        return loadFromJsonListFavoriteResponse(Application.appContext)
    }

    suspend fun getAllIngredients(): ListIngredientResponse {
        return loadFromJsonListIngredientResponse(Application.appContext)
    }

    suspend fun getUserIngredients(
        userId: String,
    ): ListIngredientResponse {
        return loadFromJsonListUserIngredientResponse(Application.appContext)
    }

    suspend fun getDetailRecipeById(
        recipeId: String,
    ): DetailRecipeResponse {
        return loadFromJsonDetailRecipeResponse(Application.appContext)
    }

    companion object {
//        @Volatile
//        private var INSTANCE: DummyApiService? = null
//        fun getInstance(context: Context): DummyApiService {
//            return INSTANCE ?: synchronized(this) {
//                val instance = DummyApiService(context)
//                INSTANCE = instance
//                instance
//            }
//        }

    }
}