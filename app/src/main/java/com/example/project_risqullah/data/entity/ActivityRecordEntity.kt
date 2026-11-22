package com.example.project_risqullah.data.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "activity_records")
data class ActivityRecordEntity(

    @PrimaryKey
    val id: String,

    val name: String,

    val category: String,

    val amount: Double,

    val unit: String,

    val createdAt: Long
)