package com.example.project_risqullah.Home.pertemuan_6

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.project_risqullah.R

class SatuFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return inflater.inflate(
            R.layout.fragment_satu,
            container,
            false
        )
    }

    override fun onViewCreated(
        view: View,
        savedInstanceState: Bundle?
    ) {
        super.onViewCreated(view, savedInstanceState)

        // Baca username yang tersimpan
        val sharedPref = requireContext().getSharedPreferences(
            "user_pref",
            Context.MODE_PRIVATE
        )

        val username = sharedPref.getString(
            "username",
            "Pengguna"
        ) ?: "Pengguna"

        // Tampilkan username di Fragment pertama
        val tvNama = view.findViewById<TextView>(
            R.id.tvNama
        )

        tvNama.text = "Halo, $username"
    }
}