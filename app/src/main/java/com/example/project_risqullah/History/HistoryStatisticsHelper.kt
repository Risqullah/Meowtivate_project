package com.example.project_risqullah.History

import com.example.project_risqullah.data.entity.ActivityRecordEntity
import java.util.Calendar
import java.util.Locale

data class WeeklyStatistics(
    val totalSteps: Double,
    val averageWaterMl: Double,
    val averageSleepHours: Double,
    val dailySteps: List<Double>
)

class HistoryStatisticsHelper {

    private data class DailyTotals(
        var steps: Double = 0.0,
        var water: Double = 0.0,
        var sleep: Double = 0.0
    )

    // Menghitung statistik tujuh hari terakhir
    fun calculate(
        records: List<ActivityRecordEntity>,
        now: Long = System.currentTimeMillis()
    ): WeeklyStatistics {

        val today = Calendar.getInstance().apply {
            timeInMillis = now

            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val dayStarts = (0 until 7).map { index ->

            (today.clone() as Calendar).apply {
                add(Calendar.DAY_OF_YEAR, index - 6)
            }.timeInMillis
        }

        val nextDay = (today.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, 1)
        }.timeInMillis

        // Menyiapkan total setiap hari
        val dailyTotals = dayStarts.associateWith {
            DailyTotals()
        }.toMutableMap()

        // Mengelompokkan aktivitas berdasarkan tanggal
        records.forEach { record ->

            if (
                record.createdAt < dayStarts.first() ||
                record.createdAt >= nextDay
            ) {
                return@forEach
            }

            val recordDay = Calendar.getInstance().apply {
                timeInMillis = record.createdAt

                set(Calendar.HOUR_OF_DAY, 0)
                set(Calendar.MINUTE, 0)
                set(Calendar.SECOND, 0)
                set(Calendar.MILLISECOND, 0)
            }.timeInMillis

            val total = dailyTotals[recordDay]
                ?: return@forEach

            when (record.category.trim().lowercase(Locale.ROOT)) {

                "langkah" -> {
                    total.steps += record.amount
                }

                "air putih" -> {
                    total.water += record.amount
                }

                "tidur" -> {
                    total.sleep += record.amount
                }
            }
        }

        val totalSteps = dailyTotals.values.sumOf {
            it.steps
        }

        val totalWater = dailyTotals.values.sumOf {
            it.water
        }

        val totalSleep = dailyTotals.values.sumOf {
            it.sleep
        }

        val dailySteps = dayStarts.map { date ->
            dailyTotals[date]?.steps ?: 0.0
        }

        return WeeklyStatistics(
            totalSteps = totalSteps,
            averageWaterMl = totalWater / 7.0,
            averageSleepHours = totalSleep / 7.0,
            dailySteps = dailySteps
        )
    }
}
