package com.example.project_risqullah.Home.pertemuan_10

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.project_risqullah.databinding.ItemWellnessBinding

class WellnessAdapter(
    private val wellnessList: List<WellnessModel>,
    private val onItemClick: (WellnessModel) -> Unit
) : RecyclerView.Adapter<WellnessAdapter.WellnessViewHolder>() {

    // ViewHolder menyimpan komponen dari item_wellness.xml
    inner class WellnessViewHolder(
        val binding: ItemWellnessBinding
    ) : RecyclerView.ViewHolder(binding.root)

    // Membuat tampilan item dari XML
    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): WellnessViewHolder {

        val binding = ItemWellnessBinding.inflate(
            LayoutInflater.from(parent.context),
            parent,
            false
        )

        return WellnessViewHolder(binding)
    }

    // Mengisi tampilan dengan data
    override fun onBindViewHolder(
        holder: WellnessViewHolder,
        position: Int
    ) {
        val item = wellnessList[position]

        with(holder.binding) {

            tvWellnessIcon.text = item.icon

            iconContainer.setBackgroundResource(
                item.iconBackgroundRes
            )

            tvWellnessTitle.text = item.title

            tvWellnessDescription.text = item.description

            // Aksi ketika kartu ditekan
            root.setOnClickListener {
                onItemClick(item)
            }
        }
    }

    // Menghitung jumlah item
    override fun getItemCount(): Int {
        return wellnessList.size
    }
}