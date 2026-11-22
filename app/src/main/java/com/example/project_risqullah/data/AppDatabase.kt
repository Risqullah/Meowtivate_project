package com.example.project_risqullah.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.project_risqullah.data.dao.ActivityRecordDao
import com.example.project_risqullah.data.dao.PetXpDao
import com.example.project_risqullah.data.entity.ActivityRecordEntity
import com.example.project_risqullah.data.entity.PetXpEventEntity

@Database(
    entities = [
        ActivityRecordEntity::class,
        PetXpEventEntity::class
    ],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityRecordDao(): ActivityRecordDao

    abstract fun petXpDao(): PetXpDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Migrasi database versi 1 ke versi 2
        private val MIGRATION_1_2 = object : Migration(1, 2) {

            override fun migrate(
                db: SupportSQLiteDatabase
            ) {

                db.execSQL(
                    """
                    CREATE TABLE IF NOT EXISTS pet_xp_events (
                        id TEXT NOT NULL,
                        sourceType TEXT NOT NULL,
                        category TEXT NOT NULL,
                        xp INTEGER NOT NULL,
                        createdAt INTEGER NOT NULL,
                        dayKey TEXT NOT NULL,
                        activityRecordId TEXT,
                        PRIMARY KEY(id)
                    )
                    """.trimIndent()
                )
            }
        }

        fun getInstance(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meowtivate_database"
                )
                    .addMigrations(MIGRATION_1_2)
                    .build()
                    .also {

                        INSTANCE = it

                    }
            }
        }
    }
}
