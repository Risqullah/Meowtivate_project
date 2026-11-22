package com.example.project_risqullah.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.project_risqullah.data.entity.PetXpEventEntity

@Dao
interface PetXpDao {

    // Menyimpan perolehan XP.
    // Jika ID sudah ada, data tidak dimasukkan kembali.
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(event: PetXpEventEntity): Long

    // Mengambil seluruh riwayat XP.
    @Query(
        "SELECT * FROM pet_xp_events ORDER BY createdAt DESC"
    )
    suspend fun getAll(): List<PetXpEventEntity>

    // Menghitung total XP.
    @Query(
        "SELECT COALESCE(SUM(xp), 0) FROM pet_xp_events"
    )
    suspend fun getTotalXp(): Int

    // Menghitung XP aktivitas berdasarkan kategori dan tanggal.
    @Query(
        """
        SELECT COUNT(*) FROM pet_xp_events
        WHERE sourceType = 'ACTIVITY'
        AND category = :category
        AND dayKey = :dayKey
        """
    )
    suspend fun getActivityCount(
        category: String,
        dayKey: String
    ): Int

    // Memeriksa bonus target harian.
    @Query(
        """
        SELECT COUNT(*) FROM pet_xp_events
        WHERE sourceType = 'DAILY_BONUS'
        AND category = :category
        AND dayKey = :dayKey
        """
    )
    suspend fun getDailyBonusCount(
        category: String,
        dayKey: String
    ): Int
}
