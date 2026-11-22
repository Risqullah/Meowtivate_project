package com.example.project_risqullah.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project_risqullah.R
import com.example.project_risqullah.data.AppDatabase
import com.example.project_risqullah.databinding.FragmentHistoryOverviewBinding
import kotlinx.coroutines.launch
import java.text.NumberFormat
import java.util.Locale
import kotlin.math.roundToInt
import java.text.SimpleDateFormat
import java.util.Calendar

class HistoryOverviewFragment : Fragment() {

    private var _binding: FragmentHistoryOverviewBinding? = null

    private val binding get() = _binding!!

    private val statisticsHelper = HistoryStatisticsHelper()

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

    // Mengambil data statistik dari Room
    private fun refreshStatistics() {

        val context = requireContext()

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val database = AppDatabase.getInstance(context)

                val records = database
                    .activityRecordDao()
                    .getAll()

                if (_binding == null) {
                    return@launch
                }

                val statistics = statisticsHelper.calculate(
                    records
                )

                val locale = Locale.forLanguageTag("id-ID")

                // Total langkah
                val numberFormat = NumberFormat.getIntegerInstance(
                    locale
                )

                binding.tvWeeklyStepsTotal.text =
                    "${numberFormat.format(statistics.totalSteps.toLong())} langkah"

                // Rata-rata air putih
                val waterFormat = NumberFormat.getNumberInstance(
                    locale
                ).apply {
                    minimumFractionDigits = 1
                    maximumFractionDigits = 1
                }

                binding.tvAverageWater.text =
                    "${waterFormat.format(statistics.averageWaterMl / 1000.0)} L"

                // Rata-rata tidur
                val sleepMinutes = (
                        statistics.averageSleepHours * 60
                        ).roundToInt()

                val sleepHours = sleepMinutes / 60

                val remainingMinutes = sleepMinutes % 60

                binding.tvAverageSleep.text =
                    "${sleepHours}j ${remainingMinutes}m"

                // Grafik langkah mingguan
                updateWeeklyChart(statistics.dailySteps)

            } catch (e: Exception) {
                // Statistik tetap menggunakan teks awal
                // jika database gagal dibaca.
            }
        }
    }

    // Memperbarui tinggi grafik
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

        // Mengambil seluruh label hari
        val dayLabels = listOf(
            binding.tvMonday,
            binding.tvTuesday,
            binding.tvWednesday,
            binding.tvThursday,
            binding.tvFriday,
            binding.tvSaturday,
            binding.tvSunday
        )

        // Menentukan tanggal awal grafik
        val startDate = Calendar.getInstance().apply {
            add(Calendar.DAY_OF_YEAR, -6)
        }

        // Format nama hari Indonesia
        val dayFormat = SimpleDateFormat(
            "EEE",
            Locale.forLanguageTag("id-ID")
        )

        // Memperbarui label sesuai tanggal
        dayLabels.forEach { label ->

            label.text = dayFormat.format(startDate.time)

            startDate.add(Calendar.DAY_OF_YEAR, 1)
        }

        val maxSteps = dailySteps.maxOrNull() ?: 0.0

        val density = resources.displayMetrics.density

        bars.forEachIndexed { index, bar ->

            val value = dailySteps.getOrElse(index) {
                0.0
            }

            val ratio = if (maxSteps > 0) {
                value / maxSteps
            } else {
                0.0
            }

            val newHeight = (
                    84 * density * ratio
                    ).roundToInt()

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

    // Memuat ulang statistik ketika halaman aktif kembali
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
