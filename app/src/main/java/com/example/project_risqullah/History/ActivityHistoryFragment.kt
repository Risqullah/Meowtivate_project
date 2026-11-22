package com.example.project_risqullah.History

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.project_risqullah.databinding.FragmentActivityHistoryBinding

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

        // Memuat data saat halaman dibuka
        loadActivityRecords()
    }

    // Membaca data dari penyimpanan lokal
    private fun loadActivityRecords() {

        val records = ActivityRecordStorage.getAll(
            requireContext()
        )

        recordAdapter.submitList(records)

        // Mengatur tampilan ketika data kosong
        if (records.isEmpty()) {

            binding.rvActivityRecords.visibility = View.GONE

            binding.tvEmptyRecords.visibility = View.VISIBLE

        } else {

            binding.rvActivityRecords.visibility = View.VISIBLE

            binding.tvEmptyRecords.visibility = View.GONE
        }
    }

    // Memuat ulang data setiap halaman kembali aktif
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