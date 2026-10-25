package com.example.project_risqullah.Pet

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.example.project_risqullah.R

data class PetMessageItem(
    val imageResId: Int,
    val sender: String,
    val message: String
)

class CustomAdapter(
    context: Context,
    private val items: List<PetMessageItem>
) : BaseAdapter() {

    private val inflater = LayoutInflater.from(context)

    override fun getCount(): Int = items.size

    override fun getItem(position: Int): PetMessageItem =
        items[position]

    override fun getItemId(position: Int): Long =
        position.toLong()

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup
    ): View {

        val row: View
        val holder: ViewHolder

        if (convertView == null) {
            row = inflater.inflate(
                R.layout.item_message,
                parent,
                false
            )

            holder = ViewHolder(
                row.findViewById(R.id.avatarImg),
                row.findViewById(R.id.textSender),
                row.findViewById(R.id.textMessage)
            )

            row.tag = holder
        } else {
            row = convertView
            holder = row.tag as ViewHolder
        }

        val item = getItem(position)

        holder.textSender.text = item.sender
        holder.textMessage.text = item.message

        // Memuat gambar menggunakan Glide
        Glide.with(holder.avatarImg)
            .load(item.imageResId)
            .placeholder(R.drawable.mascot_mochi_menyapa)
            .error(R.drawable.mascot_mochi_menyapa)
            .into(holder.avatarImg)

        return row
    }

    private class ViewHolder(
        val avatarImg: ImageView,
        val textSender: TextView,
        val textMessage: TextView
    )
}
