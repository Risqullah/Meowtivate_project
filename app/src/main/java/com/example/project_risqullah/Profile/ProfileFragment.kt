package com.example.project_risqullah.Profile

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentProfileBinding

class ProfileFragment : Fragment() {

    private var _binding: FragmentProfileBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentProfileBinding.inflate(
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

        // Ambil username dari SharedPreferences
        val sharedPref = requireContext()
            .getSharedPreferences(
                "user_pref",
                Context.MODE_PRIVATE
            )

        binding.tvProfileName.text =
            sharedPref.getString("username", "Pengguna")
                ?: "Pengguna"

        // Data contoh untuk Tips Kebiasaan Sehat
        val tips = arrayOf(
            "Minum air putih secara teratur",
            "Luangkan waktu untuk bergerak setiap hari",
            "Jaga jadwal tidur yang cukup",
            "Catat perkembangan kebiasaan sehatmu"
        )

        // Tampilkan daftar menggunakan ArrayAdapter
        val adapter = ArrayAdapter(
            requireContext(),
            R.layout.item_habit,
            R.id.tvHabit,
            tips
        )

        binding.listHealthyHabits.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}