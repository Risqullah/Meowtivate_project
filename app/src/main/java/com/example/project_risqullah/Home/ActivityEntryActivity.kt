package com.example.project_risqullah.Home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.ActivityEntryBinding
import java.util.UUID

class ActivityEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding

    companion object {
        const val EXTRA_CATEGORY = "extra_activity_category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan layout dengan ViewBinding
        binding = ActivityEntryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur Toolbar
        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Catat Aktivitas"

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.navigationIcon?.setTint(getColor(R.color.white))

        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Mengatur komponen halaman
        setupChipGroup()
        applyInitialCategory()
        setupSaveButton()
        updateAmountHint()
    }

    // Mengatur kategori awal berdasarkan tombol yang dipilih di Home
    private fun applyInitialCategory() {

        val category = intent.getStringExtra(EXTRA_CATEGORY)

        val selectedChip = when (category) {
            "Langkah" -> R.id.chipSteps
            "Air Putih" -> R.id.chipWater
            "Tidur" -> R.id.chipSleep
            else -> R.id.chipAll
        }

        binding.chipGroupFilter.check(selectedChip)
    }

    // Mengatur perubahan pilihan kategori
    private fun setupChipGroup() {

        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, _ ->
            updateAmountHint()
        }
    }

    // Mengubah petunjuk input berdasarkan kategori
    private fun updateAmountHint() {

        binding.etDuration.hint = when (
            binding.chipGroupFilter.checkedChipId
        ) {
            R.id.chipSteps -> "Jumlah langkah kaki"
            R.id.chipWater -> "Jumlah air putih (ml)"
            R.id.chipSleep -> "Durasi tidur (jam)"
            else -> "Durasi aktivitas (menit)"
        }
    }

    // Mengambil kategori dan satuan aktivitas
    private fun getCategoryInfo(): Pair<String, String> {

        return when (binding.chipGroupFilter.checkedChipId) {
            R.id.chipSteps -> Pair("Langkah", "langkah")
            R.id.chipWater -> Pair("Air Putih", "ml")
            R.id.chipSleep -> Pair("Tidur", "jam")
            else -> Pair("Umum", "menit")
        }
    }

    // Mengatur proses penyimpanan aktivitas
    private fun setupSaveButton() {

        binding.btnSaveActivity.setOnClickListener {

            val activityName = binding.etActivityName.text
                ?.toString()
                ?.trim()
                .orEmpty()

            val amountText = binding.etDuration.text
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

            // Validasi jumlah atau durasi
            val amount = amountText.toDoubleOrNull()

            if (amount == null || amount <= 0) {

                binding.etDuration.error =
                    "Masukkan jumlah atau durasi yang benar"

                binding.etDuration.requestFocus()

                return@setOnClickListener
            }

            binding.etDuration.error = null

            val (category, unit) = getCategoryInfo()

            // Membuat objek catatan aktivitas
            val record = ActivityRecord(
                id = UUID.randomUUID().toString(),
                name = activityName,
                category = category,
                amount = amount,
                unit = unit,
                createdAt = System.currentTimeMillis()
            )

            // Menyimpan catatan ke penyimpanan lokal
            ActivityRecordStorage.add(this, record)

            Toast.makeText(
                this,
                "Aktivitas berhasil disimpan!",
                Toast.LENGTH_SHORT
            ).show()

            // Membersihkan input setelah berhasil disimpan
            binding.etActivityName.text?.clear()
            binding.etDuration.text?.clear()
            binding.textInputLayout.error = null
        }
    }
}