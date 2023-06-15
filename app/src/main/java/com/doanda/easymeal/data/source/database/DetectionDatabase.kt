package com.doanda.easymeal.data.source.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.doanda.easymeal.data.source.model.DetectionEntity

@Database(entities = [DetectionEntity::class], version = 1, exportSchema = false)
abstract class DetectionDatabase : RoomDatabase() {
    abstract fun detectionDao(): DetectionDao

    companion object {
        @Volatile
        private var instance: DetectionDatabase? = null
        fun getInstance(context: Context): DetectionDatabase =
            instance ?: synchronized(this) {
                instance ?: Room.databaseBuilder(
                    context.applicationContext,
                    DetectionDatabase::class.java, "Detection.db"
                ).build()
            }
    }
}