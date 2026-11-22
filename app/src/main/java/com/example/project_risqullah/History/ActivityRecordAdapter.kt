package com.example.project_risqullah.History

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_risqullah.databinding.ItemActivityRecordBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityRecordAdapter :
    RecyclerView.Adapter<ActivityRecordAdapter.RecordViewHolder>() {

    // Menyimpan daftar catatan aktivitas
    private var recordList = listOf<ActivityRecord>()

    // ViewHolder untuk item_activity_record.xml
    inner class RecordViewHolder(
        val binding: ItemActivityRecordBinding
    ) : RecyclerView.ViewHolder(binding.root)

    // Membuat tampilan kartu
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): RecordViewHolder {

        val binding = ItemActivityRecordBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return RecordViewHolder(binding)
    }

    // Menghubungkan data dengan komponen kartu
    override fun onBindViewHolder(
        holder: RecordViewHolder,
        position: Int
    ) {

        val record = recordList[position]

        with(holder.binding) {

            // Nama aktivitas
            tvRecordName.text = record.name

            // Kategori aktivitas
            tvRecordCategory.text = record.category

            // Format angka menggunakan format Indonesia
            val numberFormat = NumberFormat.getNumberInstance(
                Locale.forLanguageTag("id-ID")
            ).apply {
                maximumFractionDigits = 2
            }

            val amountText = numberFormat.format(record.amount)

            // Jumlah dan satuan
            tvRecordAmount.text =
                "$amountText ${record.unit}"

            // Format tanggal dan waktu
            val dateFormat = SimpleDateFormat(
                "dd MMM yyyy, HH:mm",
                Locale.forLanguageTag("id-ID")
            )

            tvRecordDate.text = dateFormat.format(
                Date(record.createdAt)
            )
        }
    }

    // Menghitung jumlah catatan
    override fun getItemCount(): Int {
        return recordList.size
    }

    // Memperbarui data RecyclerView
    fun submitList(newRecords: List<ActivityRecord>) {
        recordList = newRecords.toList()
        notifyDataSetChanged()
    }
}