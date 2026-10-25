package com.example.project_risqullah.Pet

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentPetBinding

class PetFragment : Fragment() {

    private var _binding: FragmentPetBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentPetBinding.inflate(
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

        // Data contoh pesan dari Mochi
        val messages = listOf(
            PetMessageItem(
                R.drawable.mascot_mochi_menyapa,
                "Mochi",
                "Yuk, lanjutkan kebiasaan sehatmu hari ini!"
            ),
            PetMessageItem(
                R.drawable.mascot_mochi_kediplove,
                "Mochi",
                "Jangan lupa minum air putih secara teratur."
            ),
            PetMessageItem(
                R.drawable.mascot_mochi_tutupmata,
                "Mochi",
                "Istirahat yang cukup juga bagian dari hidup sehat."
            ),
            PetMessageItem(
                R.drawable.mascot_mochi_petsystem,
                "Mochi",
                "Sedikit kemajuan setiap hari tetap berarti!"
            )
        )

        // Hubungkan data ke ListView
        binding.listPetMessages.adapter = CustomAdapter(
            requireContext(),
            messages
        )
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}