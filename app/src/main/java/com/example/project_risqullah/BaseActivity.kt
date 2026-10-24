package com.example.project_risqullah

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.fragment.app.Fragment
import com.example.project_risqullah.History.HistoryFragment
import com.example.project_risqullah.Home.HomeFragment
import com.example.project_risqullah.Pet.PetFragment
import com.example.project_risqullah.Profile.ProfileFragment
import com.example.project_risqullah.databinding.ActivityBaseBinding

class BaseActivity : AppCompatActivity() {

    private lateinit var binding: ActivityBaseBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        enableEdgeToEdge()

        binding = ActivityBaseBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Mengatur inset tanpa menambah padding bawah
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

        // Home menjadi tab awal
        binding.bottomNavView.selectedItemId = R.id.nav_home

        if (savedInstanceState == null) {
            replaceFragment(HomeFragment())
        }

        // Perpindahan tab Bottom Navigation
        binding.bottomNavView.setOnItemSelectedListener { item ->

            val fragment: Fragment? = when (item.itemId) {
                R.id.nav_home -> HomeFragment()
                R.id.nav_pet -> PetFragment()
                R.id.nav_history -> HistoryFragment()
                R.id.nav_profile -> ProfileFragment()
                else -> null
            }

            if (fragment != null) {
                replaceFragment(fragment)
                true
            } else {
                false
            }
        }
    }

    private fun replaceFragment(fragment: Fragment) {
        supportFragmentManager
            .beginTransaction()
            .replace(binding.fragmentContainer.id, fragment)
            .commit()
    }
}