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

        // Gunakan MaterialToolbar sebagai ActionBar
        setSupportActionBar(binding.detailToolbar)

        supportActionBar?.apply {
            title = "Tentang Aplikasi"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // Sesuaikan system bar dengan halaman cream
        window.statusBarColor = getColor(R.color.cream)
        window.navigationBarColor = getColor(R.color.surface_white)

        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        // Tombol kembali di Toolbar
        binding.detailToolbar.setNavigationOnClickListener {
            finish()
        }

        // Tombol Kembali di bawah kartu
        binding.btnBack.setOnClickListener {
            finish()
        }
    }
}