package com.doanda.easymeal.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.source.database.ShoppingDao
import com.doanda.easymeal.data.source.model.ShoppingItemEntity
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.utils.Result

class ShoppingRepository(
    private val apiService: ApiService,
    private val shoppingDao: ShoppingDao
) {
    fun getShoppingList(userId: Int) : LiveData<Result<List<ShoppingItemEntity>>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getShoppingList(userId)

            val list = response.listIngredient
            val listRoom = list.map { item ->
                ShoppingItemEntity(
                    item.id,
                    item.ingName,
                    item.qty,
                    item.unit,
                    isHave = true
                )
            }
            shoppingDao.resetHave()
            shoppingDao.insertReplaceShoppingListItem(listRoom)

            Log.d(TAG, "Success getShoppingList")
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
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
            val response = apiService.addShoppingListItem(userId, ingId, qty, unit)

            val item = shoppingDao.getShoppingListItemById(ingId)
            if (item != null) {
                item.qty = qty
                item.unit = unit
                item.isHave = true
                shoppingDao.updateShoppingListItem(item)
            }

            Log.d(TAG, "Success addShoppingListItem")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deleteShoppingListItem(userId: Int, ingId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.deleteShoppingListItem(userId, ingId)

            val item = shoppingDao.getShoppingListItemById(ingId)
            if (item != null) {
                item.isHave = false
                shoppingDao.updateShoppingListItem(item)
            }

            Log.d(TAG, "Success deleteShoppingListItem")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun getShoppingListByIds(listId: List<Int>) = shoppingDao.getShoppingListByIds(listId)
    fun getShoppingListLocal() = shoppingDao.getShoppingList()
    suspend fun clearShoppingList() = shoppingDao.resetHave()

    companion object {
        private const val TAG = "ShoppingRepositoryLoggg"

        @Volatile
        private var INSTANCE : ShoppingRepository? = null
        fun getInstance(
            apiService: ApiService,
            shoppingDao: ShoppingDao
        ) : ShoppingRepository =
            INSTANCE?: synchronized(this) {
                INSTANCE?: ShoppingRepository(apiService, shoppingDao)
            }.also { INSTANCE = it }
    }
}