package com.doanda.easymeal.data.source.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doanda.easymeal.data.source.model.ShoppingItemEntity

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shoppingItem")
    fun getShoppingList(): LiveData<List<ShoppingItemEntity>>

    @Query("SELECT * FROM shoppingItem where isHave = 1")
    fun getHaveShopingList(): LiveData<List<ShoppingItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShoppingList(listShopping: List<ShoppingItemEntity>)

    @Update
    suspend fun updateShoppingListItem(shoppingItemEntity: ShoppingItemEntity)

    @Query("DELETE FROM shoppingItem WHERE isHave = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM shoppingItem WHERE id = :id AND isHave = 1")
    suspend fun isHaveShoppingListItem(id: Int): Boolean
}