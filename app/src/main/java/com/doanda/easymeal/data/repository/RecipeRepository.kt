package com.doanda.easymeal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.doanda.easymeal.R
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
import com.doanda.easymeal.data.response.recipe.ListRecipeItem
import com.doanda.easymeal.data.response.recipe.ListRecipeResponse
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.data.source.remote.DummyApiService
import com.doanda.easymeal.utils.Result

class RecipeRepository(
    private val apiService: ApiService,
    private val dummyApiService: DummyApiService,
) {

    fun getAllRecipes(): LiveData<Result<ListRecipeResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getAllRecipes()
            val response = dummyApiService.getAllRecipes()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getRecipeById(recipeId: Int): LiveData<Result<DetailRecipeResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getDetailRecipeById(recipeId)
            val response = dummyApiService.getDetailRecipeById(recipeId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getRecommendedRecipes(userId: Int): LiveData<Result<ListRecipeResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getRecommendedRecipes(userId)
            val response = dummyApiService.getRecommendedRecipes(userId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getFavoriteRecipes(userId: Int) : LiveData<Result<ListRecipeResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getFavoriteRecipes(userId)
            val response = dummyApiService.getFavoriteRecipes(userId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addFavoriteRecipe(userId: Int, recipeId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.addFavoriteRecipe(userId, recipeId)
            val response = dummyApiService.addFavoriteRecipe(userId, recipeId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deleteFavoriteRecipe(userId: Int, recipeId: Int) : LiveData<Result<GeneralResponse>>
            = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.deleteFavoriteRecipe(userId, recipeId)
            val response = dummyApiService.deleteFavoriteRecipe(userId, recipeId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object {
        @Volatile
        private var INSTANCE: RecipeRepository? = null
        fun getInstance(
            apiService: ApiService,
            dummyApiService: DummyApiService,
        ): RecipeRepository =
            INSTANCE?: synchronized(this) {
                INSTANCE?: RecipeRepository(apiService, dummyApiService)
            }.also { INSTANCE = it }
    }
}

