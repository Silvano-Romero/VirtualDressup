package com.example.virtualdressup2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GalleryAdapter(
    private val outfitList: List<RecyclerItem>,
    private val onItemClick: (RecyclerItem) -> Unit
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gallery_list, parent, false)
        return GalleryViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val currentItem = outfitList[position]
        holder.bind(currentItem)
        holder.itemView.isSelected = position == selectedPosition
    }

    override fun getItemCount() = outfitList.size

    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.outfitImage)
        private val tvHeading: TextView = itemView.findViewById(R.id.outfitName)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position
                    onItemClick(outfitList[position])
                    notifyDataSetChanged()
                }
            }
        }

        fun bind(outfit: RecyclerItem) {
            titleImage.setImageResource(outfit.titleImage)
            tvHeading.text = outfit.heading
        }
    }
}
