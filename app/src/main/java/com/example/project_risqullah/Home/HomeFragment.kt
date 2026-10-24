package com.example.project_risqullah.Home

import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.PopupMenu
import com.example.project_risqullah.DetailActivity
import com.example.project_risqullah.Home.pertemuan_6.SixthActivity
import com.example.project_risqullah.LoginActivity
import com.example.project_risqullah.R
import com.example.project_risqullah.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(
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

        // Membaca username dari SharedPreferences
        val sharedPref = requireContext()
            .getSharedPreferences("user_pref", Context.MODE_PRIVATE)

        val username = sharedPref.getString(
            "username",
            "Pengguna"
        ) ?: "Pengguna"

        binding.tvWelcome.text = "Halo, $username"

        // Menu aplikasi pada Dashboard
        binding.btnDetail.setOnClickListener { anchor ->
            showDashboardMenu(anchor)
        }
    }

    private fun showDashboardMenu(anchor: View) {
        val popup = PopupMenu(requireContext(), anchor)

        popup.menu.add("Praktikum 6 - Fragment")
            .setOnMenuItemClickListener {
                startActivity(
                    Intent(requireContext(), SixthActivity::class.java)
                )
                true
            }

        popup.menu.add("Tentang Meowtivate")
            .setOnMenuItemClickListener {
                startActivity(
                    Intent(requireContext(), DetailActivity::class.java)
                )
                true
            }

        popup.menu.add("Logout")
            .setOnMenuItemClickListener {
                showLogoutConfirmation()
                true
            }

        popup.show()
    }

    private fun showLogoutConfirmation() {
        AlertDialog.Builder(requireContext())
            .setTitle("Konfirmasi Logout")
            .setMessage(
                "Apakah kamu yakin ingin keluar dari akun Meowtivate?"
            )
            .setPositiveButton("Ya") { _, _ ->

                // Hapus sesi login
                requireContext()
                    .getSharedPreferences(
                        "user_pref",
                        Context.MODE_PRIVATE
                    )
                    .edit()
                    .clear()
                    .apply()

                // Kembali ke Login dan bersihkan back stack
                val intent = Intent(
                    requireContext(),
                    LoginActivity::class.java
                ).apply {
                    flags = Intent.FLAG_ACTIVITY_NEW_TASK or
                            Intent.FLAG_ACTIVITY_CLEAR_TASK
                }

                startActivity(intent)
            }
            .setNegativeButton("Tidak", null)
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}