package com.example.project_risqullah.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.project_risqullah.data.dao.ActivityRecordDao
import com.example.project_risqullah.data.entity.ActivityRecordEntity

@Database(
    entities = [ActivityRecordEntity::class],
    version = 1,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun activityRecordDao(): ActivityRecordDao

    companion object {

        @Volatile
        private var INSTANCE: AppDatabase? = null

        fun getInstance(context: Context): AppDatabase {

            return INSTANCE ?: synchronized(this) {

                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "meowtivate_database"
                ).build().also {

                    INSTANCE = it

                }
            }
        }
    }
}