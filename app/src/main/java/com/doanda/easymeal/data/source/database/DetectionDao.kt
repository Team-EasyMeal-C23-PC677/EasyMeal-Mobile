package com.doanda.easymeal.data.source.database

import androidx.room.*
import com.doanda.easymeal.data.source.model.DetectionEntity

@Dao
interface DetectionDao {
    @Query("SELECT * FROM detection")
    fun getDetections(): List<DetectionEntity>

    @Query("SELECT * FROM detection WHERE isHave = 1")
    fun getPantryDetections(): List<DetectionEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertDetections(listDetection: List<DetectionEntity>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertReplaceDetections(listDetection: List<DetectionEntity>)

    @Update
    fun updateDetection(detection: DetectionEntity)

    @Query("DELETE FROM detection")
    fun deleteAll()
}