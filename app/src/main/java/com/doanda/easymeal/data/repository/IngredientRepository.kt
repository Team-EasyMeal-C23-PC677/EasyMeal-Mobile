package com.doanda.easymeal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.pantry.ListIngredientResponse
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.data.source.remote.DummyApiService
import com.doanda.easymeal.utils.Result

class IngredientRepository(
    private val apiService: ApiService,
    private val dummyApiService: DummyApiService,
) {

    fun getAllIngredients() : LiveData<Result<ListIngredientResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getAllIngredients()
            val response = dummyApiService.getAllIngredients()
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getPantryIngredients(userId: Int) : LiveData<Result<ListIngredientResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getPantryIngredients(userId)
            val response = dummyApiService.getPantryIngredients(userId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addPantryIngredient(userId: Int, ingId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.addPantryIngredient(userId, ingId)
            val response = dummyApiService.addPantryIngredient(userId, ingId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deletePantryIngredient(userId: Int, ingId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.deletePantryIngredient(userId, ingId)
            val response = dummyApiService.deletePantryIngredient(userId, ingId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    companion object {
        @Volatile
        private var INSTANCE: IngredientRepository? = null
        fun getInstance(
            apiService: ApiService,
            dummyApiService: DummyApiService,
        ) : IngredientRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: IngredientRepository(apiService, dummyApiService)
            }.also { INSTANCE = it }
    }
}