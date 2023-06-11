package com.doanda.easymeal.data.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.response.shoppinglist.ShoppingListResponse
import com.doanda.easymeal.data.source.database.ShoppingDao
import com.doanda.easymeal.data.source.model.IngredientEntity
import com.doanda.easymeal.data.source.model.ShoppingItemEntity
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.data.source.remote.DummyApiService
import com.doanda.easymeal.utils.Result

class ShoppingRepository(
    private val apiService: ApiService,
    private val dummyApiService: DummyApiService,
    private val shoppingDao: ShoppingDao
) {

    fun getShoppingList(userId: Int) : LiveData<Result<List<ShoppingItemEntity>>>
    = liveData {
        emit(Result.Loading)
        try {
//            val response = apiService.getShoppingList(userId)
            val response = dummyApiService.getShoppingList(userId)

            val list = response.shoppingList
            val listRoom = list.map { item ->
                ShoppingItemEntity(
                    item.id,
                    item.ingName,
                    item.qty,
                    item.unit,
                    isHave = true
                )
            }
            shoppingDao.deleteAll()
            shoppingDao.insertReplaceShoppingListItem(listRoom)
        } catch (e: Exception) {
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<ShoppingItemEntity>>> =
            shoppingDao.getShoppingList().map { Result.Success(it) }
        emitSource(localData)
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
            dummyApiService: DummyApiService,
            shoppingDao: ShoppingDao
        ) : ShoppingRepository =
            INSTANCE?: synchronized(this) {
                INSTANCE?: ShoppingRepository(apiService, dummyApiService, shoppingDao)
            }.also { INSTANCE = it }
    }
}