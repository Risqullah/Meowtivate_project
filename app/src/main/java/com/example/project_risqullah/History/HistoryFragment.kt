package com.example.project_risqullah.History

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SimpleAdapter
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentHistoryBinding

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

        // Data contoh aktivitas Meowtivate
        val activityData = listOf(
            hashMapOf<String, Any>(
                "image" to R.drawable.mascot_mochi_menyapa,
                "sender" to "Langkah kaki",
                "message" to "Aktivitas berjalan dicatat hari ini."
            ),
            hashMapOf<String, Any>(
                "image" to R.drawable.mascot_mochi_kediplove,
                "sender" to "Air putih",
                "message" to "Jangan lupa memenuhi kebutuhan air putih."
            ),
            hashMapOf<String, Any>(
                "image" to R.drawable.mascot_mochi_tutupmata,
                "sender" to "Tidur",
                "message" to "Jaga rutinitas tidur yang cukup."
            ),
            hashMapOf<String, Any>(
                "image" to R.drawable.mascot_mochi_petsystem,
                "sender" to "Target harian",
                "message" to "Terus bangun kebiasaan sehat bersama Mochi."
            )
        )

        // Nama key yang akan dihubungkan ke setiap komponen
        val from = arrayOf(
            "image",
            "sender",
            "message"
        )

        // ID komponen pada item_message.xml
        val to = intArrayOf(
            R.id.avatarImg,
            R.id.textSender,
            R.id.textMessage
        )

        // Membuat dan memasang SimpleAdapter
        val adapter = SimpleAdapter(
            requireContext(),
            activityData,
            R.layout.item_message,
            from,
            to
        )

        binding.listRecentActivities.adapter = adapter
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}