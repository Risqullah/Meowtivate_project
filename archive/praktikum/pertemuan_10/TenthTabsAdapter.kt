package com.example.project_risqullah.archive.praktikum.pertemuan_10

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter

class TenthTabsAdapter(
    activity: FragmentActivity
) : FragmentStateAdapter(activity) {

    // Jumlah tab yang tersedia
    override fun getItemCount(): Int = 2

    // Menentukan Fragment berdasarkan posisi tab
    override fun createFragment(position: Int): Fragment {

        return when (position) {
            0 -> TabHabitFragment()
            1 -> TabTipsFragment()

            else -> throw IllegalStateException(
                "Posisi tab tidak valid"
            )
        }
    }
}