package com.doanda.easymeal.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doanda.easymeal.data.source.model.IngredientEntity

@Database(entities = [IngredientEntity::class], version = 1, exportSchema = false)
abstract class IngredientDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao

    companion object {
        @Volatile
        private var instance: IngredientDatabase? = null
        fun getInstance(context: Context): IngredientDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    IngredientDatabase::class.java, "Ingredient.db"
                ).build()
            }
    }
}