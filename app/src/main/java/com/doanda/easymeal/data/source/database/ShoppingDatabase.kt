package com.doanda.easymeal.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doanda.easymeal.data.source.model.ShoppingItemEntity

@Database(entities = [ShoppingItemEntity::class], version = 1, exportSchema = false)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingDao(): ShoppingDao

    companion object {
        @Volatile
        private var instance: ShoppingDatabase? = null
        fun getInstance(context: Context): ShoppingDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    ShoppingDatabase::class.java, "Shopping.db"
                ).build()
            }
    }
}