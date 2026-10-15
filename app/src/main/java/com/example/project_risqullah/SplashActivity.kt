package com.example.project_risqullah

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.lifecycleScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_splash)

        window.statusBarColor = getColor(R.color.navy)
        window.navigationBarColor = getColor(R.color.navy)

        lifecycleScope.launch {
            // Menampilkan splash selama 2 detik
            delay(2000)

            // Membaca status login sesuai modul
            val sharedPref = getSharedPreferences(
                "user_pref",
                MODE_PRIVATE
            )

            val isLogin = sharedPref.getBoolean(
                "isLogin",
                false
            )

            // Menentukan halaman tujuan
            val target = if (isLogin) {
                MainActivity::class.java
            } else {
                LoginActivity::class.java
            }

            startActivity(Intent(this@SplashActivity, target))
            finish()
        }
    }
}