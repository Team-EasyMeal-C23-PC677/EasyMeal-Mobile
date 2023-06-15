package com.doanda.easymeal.data.source.database

import androidx.lifecycle.LiveData
import androidx.room.*
import com.doanda.easymeal.data.source.model.RecipeEntity

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipe")
    fun getAllRecipes(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE isFavorite = 1")
    fun getFavoriteRecipes(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE isRecommended = 1 ORDER BY `order`")
    fun getRecommendedRecipes(): LiveData<List<RecipeEntity>>

    @Query("SELECT * FROM recipe WHERE id = :id")
    suspend fun getRecipeById(id: Int): RecipeEntity

    @Query("SELECT * FROM recipe WHERE id IN (:listId)")
    suspend fun getRecipesByIds(listId: List<Int>): List<RecipeEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insertRecipes(listRecipe: List<RecipeEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertReplaceRecipes(listRecipe: List<RecipeEntity>)

    @Insert(onConflict= OnConflictStrategy.REPLACE)
    suspend fun insertRecipe(recipeEntity: RecipeEntity)

    @Update
    suspend fun updateRecipe(recipe: RecipeEntity)

    @Query("DELETE FROM recipe WHERE isFavorite = 0")
    suspend fun deleteAll()

    @Query("DELETE from recipe WHERE id IN (:listId)")
    suspend fun deleteRecipes(listId: List<Int>)

    @Delete
    suspend fun deleteRecipe(recipe: RecipeEntity)

    @Query("UPDATE recipe SET isRecommended = false")
    suspend fun resetRecommended()

    @Query("UPDATE recipe SET isFavorite = false, isRecommended = false, `order` = 0")
    suspend fun resetRecipes()

    @Query("SELECT EXISTS(SELECT * FROM recipe WHERE id = :id AND isFavorite = 1)")
    suspend fun isRecipeFavorite(id: Int): Boolean

    @Query("SELECT recipe.isFavorite FROM recipe WHERE id = :id")
    fun isRecipeFavoriteObserve(id: Int): LiveData<Boolean>

    @Query("SELECT EXISTS(SELECT * FROM recipe WHERE id = :id AND isRecommended = 1)")
    suspend fun isRecipeRecommended(id: Int): Boolean


}