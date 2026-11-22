package com.example.project_risqullah.History

import androidx.fragment.app.Fragment
import androidx.viewpager2.adapter.FragmentStateAdapter

class HistoryTabsAdapter(
    fragment: Fragment
) : FragmentStateAdapter(fragment) {

    override fun getItemCount(): Int = 2

    override fun createFragment(position: Int): Fragment {

        return when (position) {

            0 -> HistoryOverviewFragment()

            1 -> ActivityHistoryFragment()

            else -> throw IllegalStateException(
                "Posisi tab tidak valid"
            )
        }
    }
}