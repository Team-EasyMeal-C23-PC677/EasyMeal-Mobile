package com.doanda.easymeal.data.source.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doanda.easymeal.data.source.model.ShoppingItemEntity

@Dao
interface ShoppingDao {
    @Query("SELECT * FROM shoppingItem where isHave = 1")
    fun getShoppingList(): LiveData<List<ShoppingItemEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertShoppingListItem(listShopping: List<ShoppingItemEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplaceShoppingListItem(listShopping: List<ShoppingItemEntity>)

    @Update
    suspend fun updateShoppingListItem(shoppingItemEntity: ShoppingItemEntity)

    @Query("DELETE FROM shoppingItem")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM shoppingItem WHERE id = :id AND isHave = 1)")
    fun isHaveShoppingListItem(id: Int): Boolean

    @Query("UPDATE shoppingItem SET isHave = 0")
    suspend fun resetHave()

    @Query("SELECT * FROM shoppingItem WHERE id = :id")
    suspend fun getShoppingListItemById(id: Int): ShoppingItemEntity?

    @Query("SELECT * FROM shoppingItem WHERE id IN (:listId)")
    fun getShoppingListByIds(listId: List<Int>) : LiveData<List<ShoppingItemEntity>>
}