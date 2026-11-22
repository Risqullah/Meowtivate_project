package com.example.project_risqullah.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.example.project_risqullah.R
import com.example.project_risqullah.data.AppDatabase
import com.example.project_risqullah.databinding.FragmentHistoryBinding
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.launch

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
        binding.historyViewPager.adapter =
            HistoryTabsAdapter(this)

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

    // Memperbarui badge jumlah aktivitas
    fun refreshActivityBadge() {

        val context = context ?: return

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                // Mengambil jumlah catatan dari Room
                val count = AppDatabase.getInstance(context)
                    .activityRecordDao()
                    .getCount()

                val currentBinding = _binding
                    ?: return@launch

                // Mengambil tab Aktivitas
                val activityTab =
                    currentBinding.historyTabLayout.getTabAt(1)
                        ?: return@launch

                if (count > 0) {

                    // Membuat badge jika belum tersedia
                    val badge = activityTab.orCreateBadge

                    badge.backgroundColor =
                        ContextCompat.getColor(
                            context,
                            R.color.coral
                        )

                    badge.badgeTextColor =
                        ContextCompat.getColor(
                            context,
                            R.color.white
                        )

                    badge.number = count

                    badge.setVisible(true, false)

                } else {

                    // Menghilangkan badge jika tidak ada catatan
                    activityTab.removeBadge()
                }

            } catch (e: Exception) {

                // Tidak menampilkan badge jika gagal membaca database
                _binding?.historyTabLayout
                    ?.getTabAt(1)
                    ?.removeBadge()
            }
        }
    }

    // Memuat ulang badge ketika halaman aktif
    override fun onResume() {
        super.onResume()

        if (_binding != null) {
            refreshActivityBadge()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
