package com.example.virtualdressup2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso

// Adapter class for populating a RecyclerView with outfit items
class FemaleModelAdapter(
    private val femaleModelList: MutableList<RecyclerItem>, // List of outfit items to display
    private val onItemClick: (RecyclerItem, position: Int) -> Unit // Callback function for item click events
) : RecyclerView.Adapter<FemaleModelAdapter.FemaleModelViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION // Track the currently selected position
    var selectedModel: String? = null

    // Create a new view holder when needed
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FemaleModelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.top_row, parent, false)
        return FemaleModelViewHolder(itemView)
    }

    // Bind data to the view holder
    override fun onBindViewHolder(holder: FemaleModelViewHolder, position: Int) {
        val currentItem = femaleModelList[position]
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
    override fun getItemCount() = femaleModelList.size

    // Update onItemClick to store the selected position
    private fun onItemClick(position: Int) {
        // Store the selected position
        selectedPosition = position
        notifyDataSetChanged() // Notify adapter of data changes
    }

    fun deleteItem(i : Int){
        femaleModelList.removeAt(i)
        notifyDataSetChanged()
    }

    fun clearSelection() {
        selectedPosition = -1
        notifyDataSetChanged()
    }


    // Inner class representing the view holder for each item
    inner class FemaleModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.topsImageView)
//        private val tvHeading: TextView = itemView.findViewById(R.id.outfitName)

        // Initialize item click listener
        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    selectedPosition = position
                    onItemClick(femaleModelList[position], position) // Invoke the item click callback
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
