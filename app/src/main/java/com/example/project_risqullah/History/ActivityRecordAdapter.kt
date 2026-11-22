package com.example.project_risqullah.History

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_risqullah.data.entity.ActivityRecordEntity
import com.example.project_risqullah.databinding.ItemActivityRecordBinding
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ActivityRecordAdapter(
    private val onDeleteClick: (ActivityRecordEntity) -> Unit
) : RecyclerView.Adapter<ActivityRecordAdapter.RecordViewHolder>() {

    private var recordList = listOf<ActivityRecordEntity>()

    // ViewHolder
    inner class RecordViewHolder(
        val binding: ItemActivityRecordBinding
    ) : RecyclerView.ViewHolder(binding.root)

    // Membuat tampilan item
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

    // Menghubungkan data dengan tampilan
    override fun onBindViewHolder(
        holder: RecordViewHolder,
        position: Int
    ) {

        val record = recordList[position]

        with(holder.binding) {

            tvRecordName.text = record.name

            tvRecordCategory.text = record.category

            val numberFormat = NumberFormat.getNumberInstance(
                Locale.forLanguageTag("id-ID")
            ).apply {
                maximumFractionDigits = 2
            }

            val amountText = numberFormat.format(record.amount)

            tvRecordAmount.text =
                "$amountText ${record.unit}"

            val dateFormat = SimpleDateFormat(
                "dd MMM yyyy, HH:mm",
                Locale.forLanguageTag("id-ID")
            )

            tvRecordDate.text = dateFormat.format(
                Date(record.createdAt)
            )

            // Aksi ketika tombol Hapus ditekan
            btnDeleteRecord.setOnClickListener {
                onDeleteClick(record)
            }
        }
    }

    // Menghitung jumlah item
    override fun getItemCount(): Int {
        return recordList.size
    }

    // Memperbarui daftar aktivitas
    fun submitList(newRecords: List<ActivityRecordEntity>) {

        recordList = newRecords.toList()

        notifyDataSetChanged()
    }
}
