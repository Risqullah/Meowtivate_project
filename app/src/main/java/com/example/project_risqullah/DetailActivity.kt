package com.example.project_risqullah

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.project_risqullah.databinding.ActivityDetailBinding

class DetailActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityDetailBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Sesuaikan system bar dengan tema halaman Tentang
        window.statusBarColor = getColor(R.color.cream)
        window.navigationBarColor = getColor(R.color.surface_white)

        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        // Tombol kembali pada header
        binding.btnToolbarBack.setOnClickListener {
            finish()
        }

        // Tombol Kembali pada bagian bawah
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}