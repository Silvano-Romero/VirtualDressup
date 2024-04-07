package com.example.virtualdressup2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

// Adapter class for populating a RecyclerView with outfit items
class GalleryAdapter(
    private val outfitList: MutableList<RecyclerItem>, // List of outfit items to display
    private val onItemClick: (RecyclerItem, position: Int) -> Unit // Callback function for item click events
) : RecyclerView.Adapter<GalleryAdapter.GalleryViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION // Track the currently selected position

    // Create a new view holder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GalleryViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.gallery_list, parent, false)
        return GalleryViewHolder(itemView)
    }

    // Bind data to the view holder
    override fun onBindViewHolder(holder: GalleryViewHolder, position: Int) {
        val currentItem = outfitList[position]
        holder.bind(currentItem)
        holder.itemView.isSelected = position == selectedPosition // Highlight the selected item
    }

    // Return the total number of items in the list
    override fun getItemCount() = outfitList.size

    // Update onItemClick to store the selected position
    private fun onItemClick(position: Int) {
        // Store the selected position
        selectedPosition = position
        notifyDataSetChanged() // Notify adapter of data changes
    }

    fun deleteItem(i : Int){
        outfitList.removeAt(i)
        notifyDataSetChanged()
    }

    // Inner class representing the view holder for each item
    inner class GalleryViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.outfitImage)
        private val tvHeading: TextView = itemView.findViewById(R.id.outfitName)

        // Initialize item click listener
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position
                    onItemClick(outfitList[position], position) // Invoke the item click callback
                    notifyDataSetChanged() // Update the view to reflect the selection change
                }
            }
        }

        // Bind outfit data to the views
        fun bind(outfit: RecyclerItem) {
            titleImage.setImageResource(outfit.titleImage)
            tvHeading.text = outfit.heading
        }
    }
}
