package com.example.project_risqullah.Home

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.project_risqullah.R
import com.example.project_risqullah.data.AppDatabase
import com.example.project_risqullah.data.entity.ActivityRecordEntity
import com.example.project_risqullah.databinding.ActivityEntryBinding
import kotlinx.coroutines.launch
import java.util.UUID

class ActivityEntryActivity : AppCompatActivity() {

    private lateinit var binding: ActivityEntryBinding

    companion object {
        const val EXTRA_CATEGORY = "extra_activity_category"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

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

    // Mengatur kategori awal dari Dashboard
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

    // Mengatur perubahan kategori
    private fun setupChipGroup() {

        binding.chipGroupFilter.setOnCheckedStateChangeListener { _, _ ->
            updateAmountHint()
        }
    }

    // Mengubah petunjuk input sesuai kategori
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

    // Mengambil kategori dan satuan
    private fun getCategoryInfo(): Pair<String, String> {

        return when (binding.chipGroupFilter.checkedChipId) {
            R.id.chipSteps -> Pair("Langkah", "langkah")
            R.id.chipWater -> Pair("Air Putih", "ml")
            R.id.chipSleep -> Pair("Tidur", "jam")
            else -> Pair("Umum", "menit")
        }
    }

    // Mengatur penyimpanan aktivitas
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

            // Membuat data aktivitas untuk Room
            val record = ActivityRecordEntity(
                id = UUID.randomUUID().toString(),
                name = activityName,
                category = category,
                amount = amount,
                unit = unit,
                createdAt = System.currentTimeMillis()
            )

            // Mencegah tombol ditekan berulang kali
            binding.btnSaveActivity.isEnabled = false

            // Menyimpan data melalui DAO
            lifecycleScope.launch {

                try {

                    val database = AppDatabase.getInstance(
                        applicationContext
                    )

                    database.activityRecordDao().insert(record)

                    Toast.makeText(
                        this@ActivityEntryActivity,
                        "Aktivitas berhasil disimpan!",
                        Toast.LENGTH_SHORT
                    ).show()

                    // Membersihkan input setelah berhasil
                    binding.etActivityName.text?.clear()
                    binding.etDuration.text?.clear()
                    binding.textInputLayout.error = null

                } catch (e: Exception) {

                    Toast.makeText(
                        this@ActivityEntryActivity,
                        "Aktivitas gagal disimpan",
                        Toast.LENGTH_SHORT
                    ).show()

                } finally {

                    binding.btnSaveActivity.isEnabled = true
                }
            }
        }
    }
}