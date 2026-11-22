package com.example.project_risqullah.data

import androidx.room.withTransaction
import com.example.project_risqullah.data.entity.ActivityRecordEntity
import com.example.project_risqullah.data.entity.PetXpEventEntity
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object PetXpManager {

    private const val ACTIVITY_XP = 5
    private const val MAX_ACTIVITY_XP_PER_DAY = 3

    // Menyimpan aktivitas sekaligus memberikan XP
    suspend fun saveActivityAndAwardXp(
        database: AppDatabase,
        record: ActivityRecordEntity
    ): Boolean {

        return database.withTransaction {

            val activityDao = database.activityRecordDao()
            val xpDao = database.petXpDao()

            // Menyimpan aktivitas terlebih dahulu
            activityDao.insert(record)

            // Aktivitas umum tidak mendapatkan XP
            if (
                record.category !in listOf(
                    "Langkah",
                    "Air Putih",
                    "Tidur"
                )
            ) {
                return@withTransaction false
            }

            // Membuat tanggal berdasarkan waktu aktivitas
            val dayKey = SimpleDateFormat(
                "yyyy-MM-dd",
                Locale.US
            ).format(Date(record.createdAt))

            // Memeriksa jumlah XP aktivitas hari ini
            val activityCount = xpDao.getActivityCount(
                record.category,
                dayKey
            )

            // Maksimal tiga kali pemberian XP per kategori
            if (activityCount >= MAX_ACTIVITY_XP_PER_DAY) {
                return@withTransaction false
            }

            // Membuat ID unik berdasarkan ID aktivitas
            val xpEvent = PetXpEventEntity(
                id = "activity_${record.id}",
                sourceType = "ACTIVITY",
                category = record.category,
                xp = ACTIVITY_XP,
                createdAt = record.createdAt,
                dayKey = dayKey,
                activityRecordId = record.id
            )

            // Menyimpan perolehan XP
            val result = xpDao.insert(xpEvent)

            // true jika XP berhasil diberikan
            result != -1L
        }
    }
}
