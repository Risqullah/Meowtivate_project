package com.example.project_risqullah.Home.pertemuan_6

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.project_risqullah.LoginActivity
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.ActivitySixthBinding

class SixthActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySixthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySixthBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengaktifkan Toolbar
        setSupportActionBar(binding.toolbar)

        supportActionBar?.apply {
            title = "Meowtivate Lab"
            subtitle = "Penerapan Fragment"
            setDisplayHomeAsUpEnabled(true)
            setDisplayShowHomeEnabled(true)
            setHomeAsUpIndicator(R.drawable.ic_arrow_back)
        }

        // Ikon kembali berwarna putih pada Toolbar navy
        binding.toolbar.navigationIcon?.setTint(
            getColor(R.color.white)
        )

        // Fragment pertama tampil saat halaman dibuka
        if (savedInstanceState == null) {
            replaceFragment(
                SatuFragment(),
                addToBackStack = false
            )
        }

        // Tombol untuk mengganti isi container
        binding.btnFragment1.setOnClickListener {
            replaceFragment(SatuFragment())
        }

        binding.btnFragment2.setOnClickListener {
            replaceFragment(DuaFragment())
        }

        binding.btnFragment3.setOnClickListener {
            replaceFragment(TigaFragment())
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    private fun replaceFragment(
        fragment: Fragment,
        addToBackStack: Boolean = true
    ) {
        val transaction = supportFragmentManager
            .beginTransaction()
            .replace(
                binding.fragmentContainer.id,
                fragment
            )

        if (addToBackStack) {
            transaction.addToBackStack(null)
        }

        transaction.commit()
    }

    override fun onOptionsItemSelected(
        item: MenuItem
    ): Boolean {
        return when (item.itemId) {

            // Tombol kembali pada Toolbar
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            // Pilihan Logout dari Option Menu
            R.id.action_logout -> {
                showLogoutConfirmation()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(this)
            .setTitle("Konfirmasi Logout")
            .setMessage(
                "Apakah kamu yakin ingin keluar dari akun Meowtivate?"
            )
            .setPositiveButton("Ya") { _, _ ->

                // Hapus sesi login
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
            .setNegativeButton("Batal", null)
            .show()
    }
}