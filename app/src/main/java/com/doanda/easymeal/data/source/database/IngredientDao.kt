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

    @Update
    suspend fun updateIngredient(ingredient: IngredientEntity)

    @Query("DELETE FROM ingredient WHERE isHave = 0")
    suspend fun deleteAll()

    @Query("SELECT EXISTS(SELECT * FROM ingredient WHERE id = :id AND isHave = 1)")
    suspend fun isHaveIngredient(id: Int): Boolean
}