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

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Status bar mengikuti dashboard berwarna terang
        window.statusBarColor = getColor(R.color.cream)
        window.navigationBarColor = getColor(R.color.surface_white)

        WindowInsetsControllerCompat(
            window,
            window.decorView
        ).apply {
            isAppearanceLightStatusBars = true
            isAppearanceLightNavigationBars = true
        }

        // Menu Tentang dan Logout
        binding.btnDetail.setOnClickListener { view ->
            showDashboardMenu(view)
        }
    }

    private fun showDashboardMenu(anchor: View) {
        val popup = PopupMenu(this, anchor)

        popup.menu.add("Tentang Meowtivate")
            .setOnMenuItemClickListener {
                startActivity(
                    Intent(this, DetailActivity::class.java)
                )
                true
            }

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

                // Hapus status login dan username
                getSharedPreferences(
                    "MeowtivatePrefs",
                    MODE_PRIVATE
                ).edit()
                    .putBoolean("isLogin", false)
                    .remove("username")
                    .apply()

                // Kembali ke halaman Login
                val intent = Intent(
                    this,
                    LoginActivity::class.java
                )

                startActivity(intent)
                finish()
            }
            .setNegativeButton("Tidak", null)
            .show()
    }
}