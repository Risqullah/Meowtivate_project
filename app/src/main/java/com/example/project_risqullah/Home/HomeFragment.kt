package com.example.project_risqullah.Home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.example.project_risqullah.DetailActivity
import com.example.project_risqullah.LoginActivity
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentHomeBinding
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.example.project_risqullah.History.ActivityRecordStorage
import java.text.NumberFormat
import java.util.Calendar
import java.util.Locale
import kotlin.math.roundToInt

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHomeBinding.inflate(
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

        // Mengambil username dari SharedPreferences
        val sharedPref = requireContext()
            .getSharedPreferences(
                "user_pref",
                Context.MODE_PRIVATE
            )

        val username = sharedPref.getString(
            "username",
            "Pengguna"
        ) ?: "Pengguna"

        binding.tvWelcome.text = "Halo, $username"

        // Menu titik tiga
        binding.btnDetail.setOnClickListener { anchor ->
            showDashboardMenu(anchor)
        }

        // Menghubungkan akses cepat
        setupQuickAccess()

        // Memperbarui target harian
        refreshDailyTargets()
    }

    // =====================================
    // AKSES CEPAT DASHBOARD
    // =====================================

    private fun setupQuickAccess() {

        // Langkah kaki
        binding.btnGridSteps.setOnClickListener {
            openActivityEntry("Langkah")
        }

        // Air putih
        binding.btnGridWater.setOnClickListener {
            openActivityEntry("Air Putih")
        }

        // Durasi tidur
        binding.btnGridSleep.setOnClickListener {
            openActivityEntry("Tidur")
        }

        // XP dan Level Mochi
        binding.btnGridXp.setOnClickListener {
            navigateToTab(R.id.nav_pet)
        }

        // Riwayat aktivitas
        binding.btnGridHistory.setOnClickListener {
            navigateToTab(R.id.nav_history)
        }

        // Pengaturan target
        binding.btnGridTargets.setOnClickListener {
            navigateToTab(R.id.nav_profile)
        }
    }

    // =====================================
    // MEMBUKA HALAMAN CATAT AKTIVITAS
    // =====================================

    private fun openActivityEntry(category: String) {

        val intent = Intent(
            requireContext(),
            ActivityEntryActivity::class.java
        ).apply {

            putExtra(
                ActivityEntryActivity.EXTRA_CATEGORY,
                category
            )
        }

        startActivity(intent)
    }

    // =====================================
    // BERPINDAH KE BOTTOM NAVIGATION
    // =====================================

    private fun navigateToTab(menuItemId: Int) {

        val bottomNavigation = requireActivity()
            .findViewById<BottomNavigationView>(
                R.id.bottom_nav_view
            )

        bottomNavigation.selectedItemId = menuItemId
    }

    // =====================================
    // MENU TITIK TIGA
    // =====================================

    private fun showDashboardMenu(anchor: View) {

        val popup = PopupMenu(
            requireContext(),
            anchor
        )

        // Tentang Meowtivate
        popup.menu.add("Tentang Meowtivate")
            .setOnMenuItemClickListener {

                startActivity(
                    Intent(
                        requireContext(),
                        DetailActivity::class.java
                    )
                )

                true
            }

        // Logout
        popup.menu.add("Logout")
            .setOnMenuItemClickListener {

                showLogoutConfirmation()

                true
            }

        popup.show()
    }

    // =====================================
    // KONFIRMASI LOGOUT
    // =====================================

    private fun showLogoutConfirmation() {

        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage(
                "Apakah kamu yakin ingin keluar dari akun Meowtivate?"
            )
            .setPositiveButton("Ya") { _, _ ->

                // Menghapus sesi login
                requireContext()
                    .getSharedPreferences(
                        "user_pref",
                        Context.MODE_PRIVATE
                    )
                    .edit()
                    .clear()
                    .apply()

                // Kembali ke halaman Login
                val intent = Intent(
                    requireContext(),
                    LoginActivity::class.java
                ).apply {

                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                startActivity(intent)
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    // =====================================
    // MEMPERBARUI TARGET HARIAN
    // =====================================

    private fun refreshDailyTargets() {

        val context = requireContext()

        // Mengambil batas awal hari ini
        val today = Calendar.getInstance().apply {
            set(Calendar.HOUR_OF_DAY, 0)
            set(Calendar.MINUTE, 0)
            set(Calendar.SECOND, 0)
            set(Calendar.MILLISECOND, 0)
        }

        val startOfDay = today.timeInMillis

        // Mengambil batas awal hari berikutnya
        val tomorrow = (today.clone() as Calendar).apply {
            add(Calendar.DAY_OF_YEAR, 1)
        }

        val endOfDay = tomorrow.timeInMillis

        // Mengambil aktivitas yang dicatat hari ini
        val todayRecords = ActivityRecordStorage.getAll(context)
            .filter {
                it.createdAt >= startOfDay &&
                        it.createdAt < endOfDay
            }

        // Menghitung jumlah langkah
        val totalSteps = todayRecords
            .filter {
                it.category.equals("Langkah", ignoreCase = true)
            }
            .sumOf { it.amount }

        // Menghitung konsumsi air putih
        val totalWater = todayRecords
            .filter {
                it.category.equals("Air Putih", ignoreCase = true)
            }
            .sumOf { it.amount }

        // Menghitung durasi tidur
        val totalSleep = todayRecords
            .filter {
                it.category.equals("Tidur", ignoreCase = true)
            }
            .sumOf { it.amount }

        // Format angka Indonesia
        val numberFormat = NumberFormat.getIntegerInstance(
            Locale.forLanguageTag("id-ID")
        )

        // =================================
        // TARGET LANGKAH KAKI
        // =================================

        val stepTarget = 6000

        binding.tvStepsProgress.text =
            "${numberFormat.format(totalSteps.roundToInt())} / " +
                    "${numberFormat.format(stepTarget)}"

        binding.pbSteps.max = stepTarget

        binding.pbSteps.progress =
            totalSteps.roundToInt()
                .coerceAtLeast(0)
                .coerceAtMost(stepTarget)

        // =================================
        // TARGET AIR PUTIH
        // =================================

        val waterTarget = 1600

        binding.tvWaterProgress.text =
            "${numberFormat.format(totalWater.roundToInt())} / " +
                    "${numberFormat.format(waterTarget)} ml"

        binding.pbWater.max = waterTarget

        binding.pbWater.progress =
            totalWater.roundToInt()
                .coerceAtLeast(0)
                .coerceAtMost(waterTarget)

        // =================================
        // TARGET DURASI TIDUR
        // =================================

        val sleepTargetMinutes = 7 * 60

        val totalSleepMinutes =
            (totalSleep * 60).roundToInt()
                .coerceAtLeast(0)

        val sleepHours = totalSleepMinutes / 60
        val remainingMinutes = totalSleepMinutes % 60

        binding.tvSleepProgress.text =
            "${sleepHours}j ${remainingMinutes}m / 7j"

        binding.pbSleep.max = sleepTargetMinutes

        binding.pbSleep.progress =
            totalSleepMinutes.coerceAtMost(sleepTargetMinutes)
    }

    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            refreshDailyTargets()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}