package com.example.project_risqullah.Home.pertemuan_9

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.ActivityNinthBinding
import com.google.android.material.chip.Chip

class NinthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityNinthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityNinthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Toolbar dan tombol kembali
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Pertemuan 9"

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.navigationIcon?.setTint(getColor(R.color.white))
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        setupSaveButton()
        setupChipGroup()
        setupGridButtons()
    }

    // Tombol Simpan Aktivitas
    private fun setupSaveButton() {
        binding.btnSaveActivity.setOnClickListener {

            val activityName = binding.etActivityName.text
                ?.toString()
                ?.trim()
                .orEmpty()

            val durationText = binding.etDuration.text
                ?.toString()
                ?.trim()
                .orEmpty()

            // Validasi nama aktivitas
            if (activityName.isBlank()) {
                binding.textInputLayout.error =
                    "Nama aktivitas wajib diisi"
                binding.etActivityName.requestFocus()
                return@setOnClickListener
            }

            binding.textInputLayout.error = null

            // Validasi durasi
            val duration = durationText.toIntOrNull()

            if (duration == null || duration <= 0) {
                binding.etDuration.error =
                    "Masukkan durasi dalam menit"
                binding.etDuration.requestFocus()
                return@setOnClickListener
            }

            binding.etDuration.error = null

            val category = getSelectedCategory()

            Toast.makeText(
                this,
                "Aktivitas: $activityName\n" +
                        "Durasi: $duration menit\n" +
                        "Kategori: $category",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    // Membaca pilihan ChipGroup
    private fun setupChipGroup() {
        binding.chipGroupFilter.setOnCheckedStateChangeListener { group, checkedIds ->

            val selectedId = checkedIds.firstOrNull()

            if (selectedId != null) {
                val selectedChip =
                    group.findViewById<Chip>(selectedId)

                Toast.makeText(
                    this,
                    "Kategori: ${selectedChip.text}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    private fun getSelectedCategory(): String {
        return when (binding.chipGroupFilter.checkedChipId) {
            R.id.chipSteps -> "Langkah"
            R.id.chipWater -> "Air Putih"
            R.id.chipSleep -> "Tidur"
            else -> "Semua"
        }
    }

    // Aksi enam tombol GridLayout
    private fun setupGridButtons() {

        binding.btnGridSteps.setOnClickListener {
            showFeatureToast("Pelacak Langkah Kaki")
        }

        binding.btnGridWater.setOnClickListener {
            showFeatureToast("Pencatat Air Putih")
        }

        binding.btnGridSleep.setOnClickListener {
            showFeatureToast("Pencatat Durasi Tidur")
        }

        binding.btnGridXp.setOnClickListener {
            showFeatureToast("Sistem XP dan Level Mochi")
        }

        binding.btnGridHistory.setOnClickListener {
            showFeatureToast("Riwayat dan Statistik")
        }

        binding.btnGridTargets.setOnClickListener {
            showFeatureToast("Pengaturan Target Harian")
        }
    }

    private fun showFeatureToast(feature: String) {
        Toast.makeText(
            this,
            "Fitur: $feature",
            Toast.LENGTH_SHORT
        ).show()
    }
}