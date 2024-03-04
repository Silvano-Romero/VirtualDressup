package com.example.virtualdressup2

// OutfitAdapter.kt
// OutfitAdapter.kt
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.RecyclerItem
import com.example.virtualdressup2.R

class OutfitAdapter(
    private val outfitList: List<RecyclerItem>,
    private val onItemClick: (RecyclerItem) -> Unit
) : RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return OutfitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OutfitViewHolder, position: Int) {
        val currentItem = outfitList[position]
        holder.bind(currentItem)
        holder.itemView.isSelected = position == selectedPosition
    }

    override fun getItemCount() = outfitList.size

    inner class OutfitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
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
