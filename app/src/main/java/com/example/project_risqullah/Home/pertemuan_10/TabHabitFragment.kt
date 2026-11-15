package com.example.project_risqullah.Home.pertemuan_10

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentTabHabitBinding

class TabHabitFragment : Fragment() {

    private var _binding: FragmentTabHabitBinding? = null
    private val binding get() = _binding!!

    // Data kebiasaan sehat Meowtivate
    private val habitList = listOf(

        WellnessModel(
            "👣",
            "Langkah Kaki",
            "Target: 6.000 langkah per hari",
            R.drawable.bg_steps_icon
        ),

        WellnessModel(
            "💧",
            "Air Putih",
            "Target: 1.600 ml per hari",
            R.drawable.bg_water_icon
        ),

        WellnessModel(
            "💤",
            "Durasi Tidur",
            "Target: 7 jam per hari",
            R.drawable.bg_sleep_icon
        ),

        WellnessModel(
            "🧘",
            "Peregangan",
            "Lakukan peregangan selama 10 menit",
            R.drawable.bg_achievement_star
        ),

        WellnessModel(
            "🌤️",
            "Jalan Pagi",
            "Mulai hari dengan berjalan santai",
            R.drawable.bg_about_badge
        ),

        WellnessModel(
            "📝",
            "Catat Aktivitas",
            "Pantau aktivitas sehat setiap hari",
            R.drawable.bg_xp_pill
        )
    )

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentTabHabitBinding.inflate(
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
        binding.rvHabits.apply {

            // Daftar disusun secara vertikal
            layoutManager = LinearLayoutManager(
                requireContext()
            )

            // Memasang adapter dan aksi klik item
            adapter = WellnessAdapter(habitList) { item ->

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