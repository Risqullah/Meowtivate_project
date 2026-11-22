package com.example.project_risqullah.History

data class ActivityRecord(
    val id: String,
    val name: String,
    val category: String,
    val amount: Double,
    val unit: String,
    val createdAt: Long
)