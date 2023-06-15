package com.doanda.easymeal.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.detailrecipe.DetailRecipeResponse
import com.doanda.easymeal.data.source.database.RecipeDao
import com.doanda.easymeal.data.source.model.RecipeEntity
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.data.source.remote.DummyApiService
import com.doanda.easymeal.utils.Result

class RecipeRepository(
    private val apiService: ApiService,
    private val dummyApiService: DummyApiService,
    private val recipeDao: RecipeDao,
) {

    fun getDetailRecipeById(recipeId: Int): LiveData<Result<DetailRecipeResponse>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getDetailRecipeById(recipeId)
//            val response = dummyApiService.getDetailRecipeById(recipeId)

            Log.d(TAG, "Success getDetailRecipeById")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getRecommendedRecipes(userId: Int): LiveData<Result<List<RecipeEntity>>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getRecommendedRecipes(userId)
//            val response = dummyApiService.getRecommendedRecipes(userId)

            val listRecipe = response.listRecipe
            val listRecipeId = mutableListOf<Int>()
            val listRecipeRoom = mutableListOf<RecipeEntity>()

            for (recipe in listRecipe) {
                val isFavorite = recipeDao.isRecipeFavorite(recipe.id)
                listRecipeId.add(recipe.id)
                val recipeEntity = RecipeEntity(
                    recipe.id,
                    recipe.title,
                    recipe.description,
                    recipe.totalTime,
                    recipe.serving,
                    recipe.imgUrl,
                    isFavorite = isFavorite,
                    isRecommended = true
                )
                listRecipeRoom.add(recipeEntity)
            }

            val existingRecipes = recipeDao.getRecipesByIds(listRecipeId)
            for (recipeEntity in listRecipeRoom) {
                val existingRecipe = existingRecipes.find { it.id == recipeEntity.id}
                if (existingRecipe != null) {
                    recipeDao.updateRecipe(recipeEntity)
                } else {
                    recipeDao.insertRecipe(recipeEntity)
                }
            }
            val recipesToDelete = existingRecipes.filterNot { it.id in listRecipeId && !it.isFavorite}
            recipeDao.deleteRecipes(recipesToDelete.map {it.id})

            Log.d(TAG, "Success getRecommendedRecipes")
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<RecipeEntity>>> =
            recipeDao.getRecommendedRecipes().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getFavoriteRecipes(userId: Int) : LiveData<Result<List<RecipeEntity>>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getFavoriteRecipes(userId)
//            val response = dummyApiService.getFavoriteRecipes(userId)

            val listRecipe = response.listFavoriteRecipe
            val listRecipeRoom = listRecipe.map { recipe ->
                val isRecommended = recipeDao.isRecipeRecommended(recipe.id)
                RecipeEntity(
                    recipe.id,
                    recipe.title,
                    recipe.description,
                    recipe.totalTime,
                    recipe.serving,
                    recipe.imgUrl,
                    isFavorite = true,
                    isRecommended = isRecommended,
                )
            }
            recipeDao.resetFavorite()
            recipeDao.insertReplaceRecipes(listRecipeRoom)
            Log.d(TAG, "Success getFavoriteRecipes")
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<RecipeEntity>>> =
            recipeDao.getFavoriteRecipes().map { Result.Success(it) }
        emitSource(localData)
    }

    fun addFavoriteRecipe(userId: Int, recipeId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addFavoriteRecipe(userId, recipeId)
//            val response = dummyApiService.addFavoriteRecipe(userId, recipeId)

            val recipe = recipeDao.getRecipeById(recipeId)
            recipe.isFavorite = true
            recipeDao.updateRecipe(recipe)

            Log.d(TAG, "Success addFavoriteRecipe")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deleteFavoriteRecipe(userId: Int, recipeId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.deleteFavoriteRecipe(userId, recipeId)
//            val response = dummyApiService.deleteFavoriteRecipe(userId, recipeId)

            val recipe = recipeDao.getRecipeById(recipeId)
            recipe.isFavorite = false
            recipeDao.updateRecipe(recipe)

            Log.d(TAG, "Success deleteFavoriteRecipe")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun isRecipeFavoriteLocal(recipeId: Int) = recipeDao.isRecipeFavoriteObserve(recipeId)

    fun getFavoriteRecipesLocal() = recipeDao.getFavoriteRecipes()

    fun getRecommendedRecipesLocal() = recipeDao.getRecommendedRecipes()
    suspend fun clearFavorite() = recipeDao.resetFavorite()


    companion object {
        private const val TAG = "RecipeRepositoryLoggg"
        @Volatile
        private var INSTANCE: RecipeRepository? = null
        fun getInstance(
            apiService: ApiService,
            dummyApiService: DummyApiService,
            recipeDao: RecipeDao,
        ): RecipeRepository =
            INSTANCE?: synchronized(this) {
                INSTANCE?: RecipeRepository(apiService, dummyApiService, recipeDao)
            }.also { INSTANCE = it }
    }
}

