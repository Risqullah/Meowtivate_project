package com.example.project_risqullah

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_risqullah.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        // Menghubungkan Activity dengan activity_base.xml
        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur area system bar agar navigasi bawah
        // tidak mendapat padding tambahan di bagian bawah
        ViewCompat.setOnApplyWindowInsetsListener(binding.root) { view, insets ->
            val systemBars = insets.getInsets(
                WindowInsetsCompat.Type.systemBars()
            )

            view.setPadding(
                systemBars.left,
                systemBars.top,
                systemBars.right,
                0
            )

            insets
        }

        // Pengujian awal: pastikan setiap menu bisa diklik
        binding.bottomNavView.setOnItemSelectedListener { item ->
            when (item.itemId) {
                R.id.nav_home -> {
                    showTabMessage("Home dipilih")
                    true
                }

                R.id.nav_pet -> {
                    showTabMessage("Pet dipilih")
                    true
                }

                R.id.nav_history -> {
                    showTabMessage("Riwayat dipilih")
                    true
                }

                R.id.nav_profile -> {
                    showTabMessage("Profil dipilih")
                    true
                }

                else -> false
            }
        }
    }

    private fun showTabMessage(message: String) {
        Toast.makeText(
            this,
            message,
            Toast.LENGTH_SHORT
        ).show()
    }
}