package com.example.project_risqullah.History

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import androidx.core.content.ContextCompat
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator

class HistoryFragment : Fragment() {

    private var _binding: FragmentHistoryBinding? = null

    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentHistoryBinding.inflate(
            inflater,
            container,
            false
        )

        return binding.root
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Memasang adapter ke ViewPager2
        binding.historyViewPager.adapter = HistoryTabsAdapter(this)

        // Menghubungkan TabLayout dengan ViewPager2
        TabLayoutMediator(
            binding.historyTabLayout,
            binding.historyViewPager
        ) { tab, position ->

            when (position) {

                // TAB RINGKASAN
                0 -> {

                    tab.text = "Ringkasan"

                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_nav_history
                    )
                }

                // TAB AKTIVITAS
                1 -> {

                    tab.text = "Aktivitas"

                    tab.icon = ContextCompat.getDrawable(
                        requireContext(),
                        R.drawable.ic_edit
                    )
                }
            }

        }.attach()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}