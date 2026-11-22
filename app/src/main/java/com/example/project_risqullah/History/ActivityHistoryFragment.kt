package com.example.project_risqullah.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_risqullah.data.AppDatabase
import com.example.project_risqullah.data.entity.ActivityRecordEntity
import com.example.project_risqullah.databinding.FragmentActivityHistoryBinding
import kotlinx.coroutines.launch

class ActivityHistoryFragment : Fragment() {

    private var _binding: FragmentActivityHistoryBinding? = null

    private val binding get() = _binding!!

    private lateinit var recordAdapter: ActivityRecordAdapter

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {

        _binding = FragmentActivityHistoryBinding.inflate(
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

        // Membuat adapter dan menghubungkan aksi Hapus
        recordAdapter = ActivityRecordAdapter { record ->

            showDeleteConfirmation(record)
        }

        // Mengatur RecyclerView
        binding.rvActivityRecords.apply {
            layoutManager = LinearLayoutManager(requireContext())
            adapter = recordAdapter
            setHasFixedSize(false)
        }
    }

    // Mengambil data dari Room Database
    private fun loadActivityRecords() {

        val context = requireContext()

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val database = AppDatabase.getInstance(context)

                val records = database
                    .activityRecordDao()
                    .getAll()

                val currentBinding = _binding
                    ?: return@launch

                recordAdapter.submitList(records)

                // Menampilkan kondisi data kosong
                if (records.isEmpty()) {

                    currentBinding.rvActivityRecords.visibility =
                        View.GONE

                    currentBinding.tvEmptyRecords.visibility =
                        View.VISIBLE

                } else {

                    currentBinding.rvActivityRecords.visibility =
                        View.VISIBLE

                    currentBinding.tvEmptyRecords.visibility =
                        View.GONE
                }

            } catch (e: Exception) {

                val currentBinding = _binding
                    ?: return@launch

                currentBinding.rvActivityRecords.visibility =
                    View.GONE

                currentBinding.tvEmptyRecords.visibility =
                    View.VISIBLE

                currentBinding.tvEmptyRecords.text =
                    "Gagal memuat aktivitas."
            }
        }
    }

    // Menampilkan dialog konfirmasi penghapusan
    private fun showDeleteConfirmation(
        record: ActivityRecordEntity
    ) {

        AlertDialog.Builder(requireContext())
            .setTitle("Hapus Aktivitas?")
            .setMessage(
                "Apakah kamu yakin ingin menghapus catatan " +
                        "\"${record.name}\"?"
            )
            .setNegativeButton("Batal", null)
            .setPositiveButton("Hapus") { _, _ ->

                deleteActivity(record)
            }
            .show()
    }

    // Menghapus aktivitas melalui Room DAO
    private fun deleteActivity(
        record: ActivityRecordEntity
    ) {

        val context = requireContext()

        viewLifecycleOwner.lifecycleScope.launch {

            try {

                val database = AppDatabase.getInstance(context)

                database
                    .activityRecordDao()
                    .delete(record)

                // Memperbarui jumlah badge pada tab Aktivitas
                (parentFragment as? HistoryFragment)
                    ?.refreshActivityBadge()

                Toast.makeText(
                    context,
                    "Aktivitas berhasil dihapus.",
                    Toast.LENGTH_SHORT
                ).show()

                // Memuat ulang RecyclerView
                loadActivityRecords()

            } catch (e: Exception) {

                Toast.makeText(
                    context,
                    "Gagal menghapus aktivitas.",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }

    // Memuat ulang data ketika halaman aktif
    override fun onResume() {
        super.onResume()

        if (::recordAdapter.isInitialized) {
            loadActivityRecords()
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()

        _binding = null
    }
}