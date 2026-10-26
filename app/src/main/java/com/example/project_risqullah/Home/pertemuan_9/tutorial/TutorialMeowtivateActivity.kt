package com.example.project_risqullah.Home.pertemuan_9.tutorial

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.ActivityTutorialMeowtivateBinding

class TutorialMeowtivateActivity : AppCompatActivity() {

    private lateinit var binding: ActivityTutorialMeowtivateBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityTutorialMeowtivateBinding.inflate(
            layoutInflater
        )
        setContentView(binding.root)

        setSupportActionBar(binding.toolbar)
        supportActionBar?.title = "Tutorial Meowtivate"

        binding.toolbar.setNavigationIcon(R.drawable.ic_arrow_back)
        binding.toolbar.navigationIcon?.setTint(getColor(R.color.white))
        binding.toolbar.setNavigationOnClickListener {
            finish()
        }

        // Pasang adapter terlebih dahulu
        binding.tutorialViewPager.adapter =
            TutorialFragmentAdapter(this)

        // Hubungkan indikator setelah adapter terpasang
        binding.dotIndicator.attachTo(binding.tutorialViewPager)
    }
}