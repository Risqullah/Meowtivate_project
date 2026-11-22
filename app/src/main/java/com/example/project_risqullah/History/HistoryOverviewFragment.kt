package com.example.project_risqullah.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentHistoryOverviewBinding
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt


class HistoryOverviewFragment : Fragment() {

    private var _binding: FragmentHistoryOverviewBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHistoryOverviewBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        refreshStatistics()
    }

    // Memperbarui seluruh statistik
    private fun refreshStatistics() {

        val records = ActivityRecordStorage.getAll(
            requireContext()
        )

        val statistics = HistoryStatisticsHelper.calculate(
            records
        )

        val locale = Locale.forLanguageTag("id-ID")

        // Format total langkah
        val numberFormat = NumberFormat.getIntegerInstance(
            locale
        )

        binding.tvWeeklyStepsTotal.text =
            "${numberFormat.format(statistics.totalSteps.toLong())} langkah"

        // Format rata-rata air putih
        val waterFormat = NumberFormat.getNumberInstance(
            locale
        ).apply {
            minimumFractionDigits = 1
            maximumFractionDigits = 1
        }

        binding.tvAverageWater.text =
            "${waterFormat.format(statistics.averageWaterMl / 1000.0)} L"

        // Format durasi tidur dalam jam dan menit
        val sleepMinutes = (
                statistics.averageSleepHours * 60
                ).roundToInt()

        val sleepHours = sleepMinutes / 60
        val remainingMinutes = sleepMinutes % 60

        binding.tvAverageSleep.text =
            "${sleepHours}j ${remainingMinutes}m"

        // Memperbarui grafik langkah mingguan
        updateWeeklyChart(statistics.dailySteps)
    }

    // Mengatur tinggi batang grafik berdasarkan data
    private fun updateWeeklyChart(
        dailySteps: List<Double>
    ) {

        val bars = listOf(
            binding.barMonday,
            binding.barTuesday,
            binding.barWednesday,
            binding.barThursday,
            binding.barFriday,
            binding.barSaturday,
            binding.barSunday
        )

        val maxSteps = dailySteps.maxOrNull() ?: 0.0

        val density = resources.displayMetrics.density

        bars.forEachIndexed { index, bar ->

            val value = dailySteps.getOrElse(index) {
                0.0
            }

            // Menghitung proporsi tinggi grafik
            val ratio = if (maxSteps > 0) {
                value / maxSteps
            } else {
                0.0
            }

            // Tinggi maksimum batang 84dp
            val newHeight = (
                    84 * density * ratio
                    ).roundToInt()

            // Menjaga agar batang kecil tetap terlihat
            val minimumHeight = (
                    4 * density
                    ).roundToInt()

            val layoutParams = bar.layoutParams

            layoutParams.height = if (value > 0) {
                newHeight.coerceAtLeast(minimumHeight)
            } else {
                0
            }

            bar.layoutParams = layoutParams

            // Warna coral untuk hari ini
            val barColor = if (index == 6) {
                R.color.coral
            } else {
                R.color.green
            }

            bar.setBackgroundColor(
                ContextCompat.getColor(
                    requireContext(),
                    barColor
                )
            )
        }
    }

    // Memperbarui statistik ketika halaman aktif kembali
    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            refreshStatistics()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}