package com.doanda.easymeal.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.pantry.ListIngredientResponse
import com.doanda.easymeal.data.source.database.IngredientDao
import com.doanda.easymeal.data.source.model.DetectionEntity
import com.doanda.easymeal.data.source.model.IngredientEntity
import com.doanda.easymeal.data.source.model.RecipeEntity
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.data.source.remote.DummyApiService
import com.doanda.easymeal.utils.Result

class IngredientRepository(
    private val apiService: ApiService,
    private val dummyApiService: DummyApiService,
    private val ingredientDao: IngredientDao,
) {

    fun getAllIngredients() : LiveData<Result<List<IngredientEntity>>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getAllIngredients()
            val response = dummyApiService.getAllIngredients()

            val listIng = response.listIngredient
            val listIngRoom = listIng.map { ing ->
                val isHave = ingredientDao.isHaveIngredient(ing.ingId)
                IngredientEntity(
                    ing.ingId,
                    ing.categoryName,
                    ing.ingName,
                    isHave = isHave
                )
            }

            ingredientDao.deleteAll()
            ingredientDao.insertIngredients(listIngRoom)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<IngredientEntity>>> =
            ingredientDao.getAllIngredients().map { Result.Success(it) }
        emitSource(localData)
    }

    fun getPantryIngredients(userId: Int) : LiveData<Result<List<IngredientEntity>>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getPantryIngredients(userId)
            val response = dummyApiService.getPantryIngredients(userId)

            val listIng = response.listIngredient
            val listIngRoom = listIng.map { ing ->
                IngredientEntity(
                    ing.ingId,
                    ing.categoryName,
                    ing.ingName,
                    isHave = true
                )
            }
            ingredientDao.resetHave()
            ingredientDao.insertReplaceIngredients(listIngRoom)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<IngredientEntity>>> =
            ingredientDao.getPantryIngredients().map { Result.Success(it) }
        emitSource(localData)
    }

    // TODO handle fail
    fun addPantryIngredient(userId: Int, ingId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.addPantryIngredient(userId, ingId)
            val response = dummyApiService.addPantryIngredient(userId, ingId)

            val ing = ingredientDao.getIngredientById(ingId)
            ing.isHave = true
            ingredientDao.updateIngredient(ing)

            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    // TODO handle fail
    fun deletePantryIngredient(userId: Int, ingId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.deletePantryIngredient(userId, ingId)
            val response = dummyApiService.deletePantryIngredient(userId, ingId)

            val ing = ingredientDao.getIngredientById(ingId)
            ing.isHave = false
            ingredientDao.updateIngredient(ing)

            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e("IngredientRepository", e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun isPantryNotEmpty() = ingredientDao.isPantryNotEmpty()
    suspend fun isHaveIngredient(ingId: Int) = ingredientDao.isHaveIngredient(ingId)

    fun getAllIngredientsLocal() = ingredientDao.getAllIngredients()

    fun getPantryIngredientsLocal() = ingredientDao.getPantryIngredients()

    fun getIngredientsByCategoryLocal(categoryName: String) = ingredientDao.getIngredientsByCategory(categoryName)
//    fun searchIngredientByName(ingName: String): DetectionEntity {
//        val listResult = ingredientDao.searchIngredientByName(ingName)
//        if (listResult.isEmpty()) return DetectionEntity(0, "null", "null")
//
//        val result = listResult.first()
//        val isHave = ingredientDao.isHaveIngredient(result.ingId)
//        return DetectionEntity(
//            result.ingId,
//            result.categoryName,
//            result.ingName,
//            isHave = isHave,
//            confidence = null
//        )
//    }

    fun searchIngredientByName(ingName: String): LiveData<List<IngredientEntity>>
    = ingredientDao.searchIngredientByName(ingName)

    companion object {
        @Volatile
        private var INSTANCE: IngredientRepository? = null
        fun getInstance(
            apiService: ApiService,
            dummyApiService: DummyApiService,
            ingredientDao: IngredientDao,
        ) : IngredientRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: IngredientRepository(apiService, dummyApiService, ingredientDao)
            }.also { INSTANCE = it }
    }
}