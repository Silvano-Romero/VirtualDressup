package com.example.virtualdressup2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

// Adapter class for populating a RecyclerView with outfit items
class GarmentTopsAdapter(
    private val garmentTopsList: MutableList<RecyclerItem>, // List of outfit items to display
    private val onItemClick: (RecyclerItem, position: Int) -> Unit // Callback function for item click events
) : RecyclerView.Adapter<GarmentTopsAdapter.GarmentTopsViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION // Track the currently selected position

    // Create a new view holder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GarmentTopsViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.top_row, parent, false)
        return GarmentTopsViewHolder(itemView)
    }

    // Bind data to the view holder
    override fun onBindViewHolder(holder: GarmentTopsViewHolder, position: Int) {
        val currentItem = garmentTopsList[position]
        holder.bindWithImageURL(currentItem)
        holder.itemView.isSelected = position == selectedPosition // Highlight the selected item
        // Set background color based on the selected position
        if (selectedPosition == holder.adapterPosition) {
            holder.itemView.setBackgroundColor(Color.GRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }

    // Return the total number of items in the list
    override fun getItemCount() = garmentTopsList.size

    // Update onItemClick to store the selected position
    private fun onItemClick(position: Int) {
        // Store the selected position
        selectedPosition = position
        notifyDataSetChanged() // Notify adapter of data changes
    }

    fun deleteItem(i : Int){
        garmentTopsList.removeAt(i)
        notifyDataSetChanged()
    }

    // Inner class representing the view holder for each item
    inner class GarmentTopsViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.topsImageView)
//        private val tvHeading: TextView = itemView.findViewById(R.id.outfitName)

        // Initialize item click listener
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position
                    onItemClick(garmentTopsList[position], position) // Invoke the item click callback
                    notifyDataSetChanged() // Update the view to reflect the selection change
                }
            }
        }


        fun bindWithImageURL(outfit: RecyclerItem) {
            // Set view to urlImage
            Picasso.get().load(outfit.titleImageURL).into(titleImage)
//            tvHeading.text = outfit.heading
        }
    }
}
