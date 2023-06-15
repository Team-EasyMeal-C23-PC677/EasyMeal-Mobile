package com.doanda.easymeal.data.source.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doanda.easymeal.data.source.model.IngredientEntity

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredient")
    fun getAllIngredients(): LiveData<List<IngredientEntity>>

    @Query("SELECT * FROM ingredient WHERE isHave = 1")
    fun getPantryIngredients(): LiveData<List<IngredientEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertIngredients(listIngredient: List<IngredientEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplaceIngredients(listIngredient: List<IngredientEntity>)

    @Update
    suspend fun updateIngredient(ingredient: IngredientEntity)

    @Query("DELETE FROM ingredient")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM ingredient WHERE id = :id AND isHave = 1)")
    suspend fun isHaveIngredient(id: Int): Boolean
    @Query("UPDATE ingredient SET isHave = 0")
    suspend fun resetHave()

    @Query("SELECT * FROM ingredient WHERE id = :ingId")
    suspend fun getIngredientById(ingId: Int): IngredientEntity

    @Query("SELECT * FROM ingredient WHERE categoryName LIKE :categoryName")
    fun getIngredientsByCategory(categoryName: String): LiveData<List<IngredientEntity>>

    @Query("SELECT EXISTS(SELECT * FROM ingredient WHERE isHave = 1)")
    fun isPantryNotEmpty(): LiveData<Boolean>

    @Query("SELECT * FROM ingredient WHERE ingName LIKE :ingName")
    fun searchIngredientByName(ingName: String): LiveData<List<IngredientEntity>>

    @Query("SELECT * FROM ingredient WHERE id IN (:listId)")
    fun getIngredientsByIds(listId: List<Int>): LiveData<List<IngredientEntity>>
}