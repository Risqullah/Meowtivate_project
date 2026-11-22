package com.example.project_risqullah.archive.praktikum.pertemuan_10

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.ActivityTenthBinding
import com.google.android.material.tabs.TabLayoutMediator

class TenthActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTenthBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menghubungkan ViewBinding
        binding = ActivityTenthBinding.inflate(
            layoutInflater
        )

        setContentView(binding.root)

        // Mengatur Toolbar
        setSupportActionBar(binding.toolbar)

        supportActionBar?.title = "Jelajahi Meowtivate"

        binding.toolbar.setNavigationIcon(
            R.drawable.ic_arrow_back
        )

        binding.toolbar.navigationIcon?.setTint(
            getColor(R.color.white)
        )

        // Tombol kembali ke Dashboard
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Memasang adapter ke ViewPager2
        binding.viewPager.adapter = TenthTabsAdapter(this)

        // Menghubungkan TabLayout dengan ViewPager2
        TabLayoutMediator(
            binding.tabLayout,
            binding.viewPager
        ) { tab, position ->

            when (position) {

                // TAB 1: KEBIASAAN
                0 -> {
                    tab.text = "Kebiasaan"

                    tab.icon = ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_nav_home
                    )

                    // Badge jumlah kebiasaan
                    tab.getOrCreateBadge().apply {
                        isVisible = true
                        number = 6
                    }
                }

                // TAB 2: TIPS SEHAT
                1 -> {
                    tab.text = "Tips Sehat"

                    tab.icon = ContextCompat.getDrawable(
                        this,
                        R.drawable.ic_info
                    )

                    // Badge jumlah tips
                    tab.getOrCreateBadge().apply {
                        isVisible = true
                        number = 6
                    }
                }
            }

        }.attach()
    }
}