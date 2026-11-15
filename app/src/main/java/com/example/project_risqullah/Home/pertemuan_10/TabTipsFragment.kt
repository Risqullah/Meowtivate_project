package com.example.project_risqullah.Home.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentTabTipsBinding

class TabTipsFragment : Fragment() {
    private var _binding: FragmentTabTipsBinding? = null
    private val binding get() = _binding!!

    // Data tips hidup sehat Meowtivate
    private val tipsList = listOf(

        WellnessModel(
            "💧",
            "Minum Air Teratur",
            "Bagi konsumsi air putih sepanjang hari",
            R.drawable.bg_water_icon
        ),

        WellnessModel(
            "👣",
            "Mulai dari Langkah Kecil",
            "Tingkatkan aktivitas fisik secara bertahap",
            R.drawable.bg_steps_icon
        ),

        WellnessModel(
            "💤",
            "Jaga Jadwal Tidur",
            "Tidur dan bangun pada waktu yang konsisten",
            R.drawable.bg_sleep_icon
        ),

        WellnessModel(
            "🧘",
            "Lakukan Peregangan",
            "Selingi waktu duduk dengan gerakan ringan",
            R.drawable.bg_achievement_star
        ),

        WellnessModel(
            "🎯",
            "Buat Target Realistis",
            "Sesuaikan target dengan kemampuan tubuh",
            R.drawable.bg_about_badge
        ),

        WellnessModel(
            "📝",
            "Catat Perkembangan",
            "Evaluasi kebiasaan sehat secara rutin",
            R.drawable.bg_xp_pill
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTabTipsBinding.inflate(
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

        // Mengatur RecyclerView
        binding.rvTips.apply {

            // Daftar disusun secara vertikal
            layoutManager = LinearLayoutManager(
                requireContext()
            )

            // Memasang adapter dan aksi klik item
            adapter = WellnessAdapter(tipsList) { item ->

                Toast.makeText(
                    requireContext(),
                    "${item.title}\n${item.description}",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}