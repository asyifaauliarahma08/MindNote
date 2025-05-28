package com.example.mindnote

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class   CatatanAdapter(
    private val catatanList: MutableList<Catatan>,
    private val listener: OnItemClickListener
) : RecyclerView.Adapter<CatatanAdapter.CatatanViewHolder>() {

    interface OnItemClickListener {
        fun onItemClick(catatan: Catatan)
        fun onItemDelete(catatan: Catatan) // ← tambahkan ini

        // fun onItemDelete(catatan: Catatan) -> dihapus karena tidak digunakan
    }

    inner class CatatanViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val judulTextView: TextView = itemView.findViewById(R.id.tvJudul)
        val isiTextView: TextView = itemView.findViewById(R.id.tvIsi)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(catatanList[position])
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CatatanViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_catatan, parent, false)
        return CatatanViewHolder(view)
    }

    override fun onBindViewHolder(holder: CatatanViewHolder, position: Int) {
        val catatan = catatanList[position]
        holder.judulTextView.text = catatan.judul
        holder.isiTextView.text = catatan.isi
    }

    override fun getItemCount() = catatanList.size

    fun updateData(newList: List<Catatan>) {
        catatanList.clear()
        catatanList.addAll(newList)
        notifyDataSetChanged()
    }
}
