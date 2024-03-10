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
import com.myapp.revery.Garment

class OutfitAdapter(
    private val outfitList: List<RecyclerItem>,
    private val onItemClick: (RecyclerItem) -> Unit
) : RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder>() {

    // Default constructor with no parameters
    constructor() : this(emptyList(), {}) // Empty list and empty lambda


    private var selectedPosition = RecyclerView.NO_POSITION

    private var outfits: List<Garment> = listOf()

    fun setItems(outfits: List<Garment>) {
        this.outfits = outfits
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return OutfitViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: OutfitViewHolder, position: Int) {
        val currentItem = outfitList[position]
        holder.bind(currentItem)
        holder.itemView.setOnClickListener {
            onItemClick(currentItem)
        }
    }

    fun getSelectedOutfit(): Garment? {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            outfits[selectedPosition]
        } else {
            null
        }
    }

    override fun getItemCount() = outfitList.size

    inner class OutfitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.outfitImage)
        private val tvHeading: TextView = itemView.findViewById(R.id.outfitName)

        fun bind(outfit: RecyclerItem) {
            titleImage.setImageResource(outfit.titleImage)
            tvHeading.text = outfit.heading
        }
    }
}

