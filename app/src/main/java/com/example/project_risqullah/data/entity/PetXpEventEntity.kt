package com.example.project_risqullah.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "pet_xp_events")
data class PetXpEventEntity(

    @PrimaryKey
    val id: String,

    val sourceType: String,

    val category: String,

    val xp: Int,

    val createdAt: Long,

    val dayKey: String,

    val activityRecordId: String? = null
)
