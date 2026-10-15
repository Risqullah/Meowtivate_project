package com.example.project_risqullah

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_risqullah.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.btnLogin.setOnClickListener {

            val username = binding.etUsername.text
                .toString()
                .trim()

            val password = binding.etPassword.text
                .toString()

            // Validasi input kosong
            if (username.isBlank() || password.isBlank()) {

                Toast.makeText(
                    this,
                    "Isi Username dan Password terlebih dahulu",
                    Toast.LENGTH_SHORT
                ).show()

                return@setOnClickListener
            }

            // Login berhasil jika username sama dengan password
            if (username == password) {

                val sharedPref = getSharedPreferences(
                    "user_pref",
                    MODE_PRIVATE
                )

                sharedPref.edit()
                    .putBoolean("isLogin", true)
                    .putString("username", username)
                    .apply()

                Toast.makeText(
                    this,
                    "Login berhasil!",
                    Toast.LENGTH_SHORT
                ).show()

                startActivity(
                    Intent(this, MainActivity::class.java)
                )

                finish()

            } else {

                // Dialog login gagal sesuai modul
                AlertDialog.Builder(this)
                    .setTitle("Login Gagal")
                    .setMessage("Silahkan coba lagi")
                    .setPositiveButton("OK") { dialog, _ ->
                        dialog.dismiss()
                    }
                    .show()
            }
        }

        binding.btnRegister.setOnClickListener {
            Toast.makeText(
                this,
                "Fitur registrasi belum tersedia",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}