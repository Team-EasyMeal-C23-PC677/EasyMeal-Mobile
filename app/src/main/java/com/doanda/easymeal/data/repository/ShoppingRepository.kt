package com.doanda.easymeal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.shoppinglist.ShoppingListResponse
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.data.source.remote.DummyApiService
import retrofit2.http.Path
import com.doanda.easymeal.utils.Result

class ShoppingRepository(
    private val apiService: ApiService,
    private val dummyApiService: DummyApiService
) {

    fun getShoppingList(userId: Int) : LiveData<Result<ShoppingListResponse>>
            = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getShoppingList(userId)
            val response = dummyApiService.getShoppingList(userId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun addShoppingListItem(
        userId: Int,
        ingId: Int,
        qty: Float,
        unit: String,
    ) : LiveData<Result<GeneralResponse>>
            = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.addShoppingListItem(userId, ingId, qty, unit)
            val response = dummyApiService.addShoppingListItem(userId, ingId, qty, unit)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deleteShoppingListItem(userId: Int, ingId: Int) : LiveData<Result<GeneralResponse>>
            = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.deleteShoppingListItem(userId, ingId)
            val response = dummyApiService.deleteShoppingListItem(userId, ingId)
            emit(Result.Success(response))
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
    }


    companion object {
        private var INSTANCE : ShoppingRepository? = null
        fun getInstance(
            apiService: ApiService,
            dummyApiService: DummyApiService
        ) : ShoppingRepository =
            INSTANCE?: synchronized(this) {
                INSTANCE?: ShoppingRepository(apiService, dummyApiService)
            }.also { INSTANCE = it }
    }
}