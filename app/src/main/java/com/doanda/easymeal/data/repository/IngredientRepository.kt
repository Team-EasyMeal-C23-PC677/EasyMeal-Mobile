package com.doanda.easymeal.data.repository

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.liveData
import androidx.lifecycle.map
import com.doanda.easymeal.data.response.GeneralResponse
import com.doanda.easymeal.data.source.database.IngredientDao
import com.doanda.easymeal.data.source.database.ShoppingDao
import com.doanda.easymeal.data.source.model.IngredientEntity
import com.doanda.easymeal.data.source.model.ShoppingItemEntity
import com.doanda.easymeal.data.source.remote.ApiService
import com.doanda.easymeal.utils.Result

class IngredientRepository(
    private val apiService: ApiService,
    private val ingredientDao: IngredientDao,
    private val shoppingDao: ShoppingDao,
) {

    fun getAllIngredients() : LiveData<Result<List<IngredientEntity>>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.getAllIngredients()

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
            ingredientDao.resetHave()
            ingredientDao.insertReplaceIngredients(listIngRoom)
            Log.d(TAG, "Success getAllIngredients loadIngredients")
            val listShopRoom = listIng.map { ing ->
                val shopItem = shoppingDao.getShoppingListItemById(ing.ingId)
                ShoppingItemEntity(
                    id = ing.ingId,
                    ingName = ing.ingName,
                    qty = shopItem?.qty ?: 0.0f,
                    unit = shopItem?.unit ?: "",
                    isHave = shopItem?.isHave ?: false,
                )
            }
            shoppingDao.resetHave()
            shoppingDao.insertReplaceShoppingListItem(listShopRoom)
            Log.d(TAG, "Success getAllIngredients loadShoppingList")
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
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
            val response = apiService.getPantryIngredients(userId)

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
            Log.d(TAG, "Success getPantryIngredients")
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
        val localData: LiveData<Result<List<IngredientEntity>>> =
            ingredientDao.getPantryIngredients().map { Result.Success(it) }
        emitSource(localData)
    }

    fun addPantryIngredient(userId: Int, ingId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.addPantryIngredient(userId, ingId)

            val ing = ingredientDao.getIngredientById(ingId)
            ing.isHave = true
            ingredientDao.updateIngredient(ing)
            Log.d(TAG, "Success addPantryIngredient")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun deletePantryIngredient(userId: Int, ingId: Int) : LiveData<Result<GeneralResponse>>
    = liveData {
        emit(Result.Loading)
        try {
            val response = apiService.deletePantryIngredient(userId, ingId)

            val ing = ingredientDao.getIngredientById(ingId)
            ing.isHave = false
            ingredientDao.updateIngredient(ing)

            Log.d(TAG, "Success deletePantryIngredient")
            emit(Result.Success(response))
        } catch (e: Exception) {
            Log.e(TAG, e.message.toString())
            emit(Result.Error(e.message.toString()))
        }
    }

    fun isPantryNotEmpty() = ingredientDao.isPantryNotEmpty()

    fun getAllIngredientsLocal() = ingredientDao.getAllIngredients()

    fun getPantryIngredientsLocal() = ingredientDao.getPantryIngredients()

    fun getIngredientsByCategoryLocal(categoryName: String) = ingredientDao.getIngredientsByCategory(categoryName)

    fun searchIngredientByName(ingName: String): LiveData<List<IngredientEntity>>
    = ingredientDao.searchIngredientByName(ingName)

    fun getIngredientsByIds(listId: List<Int>) = ingredientDao.getIngredientsByIds(listId)
    suspend fun clearPantry() = ingredientDao.resetHave()

    companion object {
        private const val TAG = "IngredientRepositoryLoggg"

        @Volatile
        private var INSTANCE: IngredientRepository? = null
        fun getInstance(
            apiService: ApiService,
            ingredientDao: IngredientDao,
            shoppingDao: ShoppingDao,
        ) : IngredientRepository =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: IngredientRepository(apiService, ingredientDao, shoppingDao)
            }.also { INSTANCE = it }
    }
}