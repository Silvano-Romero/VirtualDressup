package com.example.virtualdressup2

import android.annotation.SuppressLint
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapp.revery.Garment
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso


class OutfitAdapter(
    private var outfitList: List<RecyclerItem>,
    private val onItemClick: (RecyclerItem, position: Int) -> Unit
) : RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder>() {

    private var selectedPosition = RecyclerView.NO_POSITION // Initially no item selected
    var onShareClick: (RecyclerItem) -> Unit = {} // Share button click listener

    private var outfits: List<Garment> = listOf() // List of garments

    // Method to update the list of items
    fun setItems(outfits: List<Garment>) {
        this.outfits = outfits
        notifyDataSetChanged() // Notify the adapter that data set has changed
    }

    // Remove an item from the outfit list
    fun removeItem(outfit: RecyclerItem) {
        outfitList = outfitList.filterNot { it.modelID == outfit.modelID }
        notifyDataSetChanged()
    }


    // Create view holder for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return OutfitViewHolder(itemView)
    }

    // Bind data to each item in the RecyclerView
    override fun onBindViewHolder(
        holder: OutfitViewHolder,
        @SuppressLint("RecyclerView") position: Int
    ) {
        val currentItem = outfitList[position]

        holder.bindWithImageURL(currentItem)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            // Update the selected position and invoke onItemClick callback
            selectedPosition = position
            onItemClick(currentItem, position)
            notifyDataSetChanged() // Update the view to reflect the selection change
        }

        // Set background color based on the selected position
        holder.itemView.isSelected = position == selectedPosition


        // Set background color based on the selected position
        if (selectedPosition == holder.adapterPosition) {
            holder.itemView.setBackgroundColor(Color.GRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }

    }


    // Return the total number of items in the list
    override fun getItemCount() = outfitList.size

    // Inner class to define the view holder for each item
    inner class OutfitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.outfitImage)
        private val tvHeading: TextView = itemView.findViewById(R.id.outfitName)

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

        // Bind data to the ViewHolder
        fun bind(outfit: RecyclerItem) {
            // Set view to urlImage
            Picasso.get().load(outfit.titleImageURL).into(titleImage)
            tvHeading.text = outfit.heading
        }

        fun bindWithImageURL(outfit: RecyclerItem) {
            // Set view to urlImage
            Picasso.get().load(outfit.titleImageURL).into(titleImage)
            tvHeading.text = outfit.heading
        }

    }


    // Function to get the selected outfit
    fun getItemAtPosition(position: Int): RecyclerItem? {
        return if (position != RecyclerView.NO_POSITION && position < outfitList.size) {
            outfitList[position]
        } else {
            null
        }
    }


}


// Add extension function to OutfitAdapter
fun OutfitAdapter.addShareButtonFunctionality(onShareClick: (RecyclerItem) -> Unit) {
    this.onShareClick = onShareClick
}

// Add a share button click listener
private var onShareClick: (RecyclerItem) -> Unit = {}