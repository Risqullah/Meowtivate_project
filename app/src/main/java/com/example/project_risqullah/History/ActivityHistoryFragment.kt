package com.example.project_risqullah.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_risqullah.data.AppDatabase
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

        // Membuat adapter
        recordAdapter = ActivityRecordAdapter()

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