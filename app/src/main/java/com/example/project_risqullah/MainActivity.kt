package com.example.project_risqullah

import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.core.view.WindowInsetsControllerCompat
import com.example.project_risqullah.databinding.ActivityMainBinding
import com.example.project_risqullah.pertemuan_6.SixthActivity

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Atur status bar agar sesuai dengan Dashboard
        window.statusBarColor = getColor(R.color.cream)
        window.navigationBarColor = getColor(R.color.surface_white)

        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        // Ambil username dari SharedPreferences
        val sharedPref = getSharedPreferences(
            "user_pref",
            MODE_PRIVATE
        )

        val username = sharedPref.getString(
            "username",
            "Pengguna"
        ) ?: "Pengguna"

        // Latihan mandiri: sapaan pengguna
        binding.tvWelcome.text = "Halo, $username"

        // Menu Tentang dan Logout
        binding.btnDetail.setOnClickListener { view ->
            showDashboardMenu(view)
        }
    }

    private fun showDashboardMenu(anchor: View) {
        val popup = PopupMenu(this, anchor)

        // Menu untuk membuka halaman Praktikum 6
        popup.menu.add("Praktikum 6 - Fragment")
            .setOnMenuItemClickListener {
                startActivity(
                    Intent(this, SixthActivity::class.java)
                )
                true
            }

        // Menu Tentang Meowtivate
        popup.menu.add("Tentang Meowtivate")
            .setOnMenuItemClickListener {
                startActivity(
                    Intent(this, DetailActivity::class.java)
                )
                true
            }

        // Menu Logout
        popup.menu.add("Logout")
            .setOnMenuItemClickListener {
                showLogoutConfirmation()
                true
            }

        popup.show()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage(
                "Apakah kamu yakin ingin keluar dari akun Meowtivate?"
            )
            .setPositiveButton("Ya") { _, _ ->

                // Hapus semua data sesi dari penyimpanan yang benar
                getSharedPreferences(
                    "user_pref",
                    MODE_PRIVATE
                ).edit()
                    .clear()
                    .apply()

                // Kembali ke Login dan bersihkan back stack
                val intent = Intent(
                    this,
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
}