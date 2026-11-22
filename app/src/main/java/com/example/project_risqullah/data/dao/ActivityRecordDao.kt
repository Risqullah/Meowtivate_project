package com.example.project_risqullah.data.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import com.example.project_risqullah.data.entity.ActivityRecordEntity

@Dao
interface ActivityRecordDao {

    // Mengambil seluruh catatan aktivitas
    @Query("SELECT * FROM activity_records ORDER BY createdAt DESC")
    suspend fun getAll(): List<ActivityRecordEntity>

    // Menyimpan catatan aktivitas
    @Insert
    suspend fun insert(record: ActivityRecordEntity)

    // Menghapus satu catatan aktivitas
    @Delete
    suspend fun delete(record: ActivityRecordEntity)

    // Menghapus seluruh catatan aktivitas
    @Query("DELETE FROM activity_records")
    suspend fun deleteAll()
}