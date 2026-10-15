package com.example.project_risqullah.pertemuan_6

import android.os.Bundle
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            android.R.id.home -> {
                onBackPressedDispatcher.onBackPressed()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }
}