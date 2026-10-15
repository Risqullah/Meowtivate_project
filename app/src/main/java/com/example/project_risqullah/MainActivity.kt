package com.example.project_risqullah

import android.content.Intent
import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_risqullah.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menerapkan ViewBinding
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Intent menuju halaman DetailActivity
        binding.btnDetail.setOnClickListener {
            val intent = Intent(this, DetailActivity::class.java)
            startActivity(intent)
        }

        // AlertDialog untuk konfirmasi Log Out
        binding.btnLogout.setOnClickListener {
            val builder = AlertDialog.Builder(this)
            builder.setTitle("Konfirmasi Log Out")
            builder.setMessage("Apakah kamu yakin ingin keluar dari aplikasi?")

            // Jika memilih Ya
            builder.setPositiveButton("Ya") { dialog, which ->
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
                finish() // Menutup MainActivity agar tidak bisa di-back
            }

            // Jika memilih Tidak
            builder.setNegativeButton("Tidak") { dialog, which ->
                dialog.dismiss() // Menutup pop-up dialog
            }

            val dialog = builder.create()
            dialog.show()
        }
    }
}
