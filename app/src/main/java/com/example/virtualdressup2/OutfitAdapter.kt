package com.example.virtualdressup2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapp.revery.Garment


class OutfitAdapter(
    private val outfitList: List<RecyclerItem>, // List of outfit items
    private val onItemClick: (RecyclerItem) -> Unit // Lambda function to handle item click events
) : RecyclerView.Adapter<OutfitAdapter.OutfitViewHolder>() {

    // Secondary constructor to create an adapter with an empty list and lambda
    constructor() : this(emptyList(), {})

    private var selectedPosition = RecyclerView.NO_POSITION // Initially no item selected
    var onShareClick: (RecyclerItem) -> Unit = {} // Share button click listener

    private var outfits: List<Garment> = listOf() // List of garments

    // Method to update the list of items
    fun setItems(outfits: List<Garment>) {
        this.outfits = outfits
        notifyDataSetChanged() // Notify the adapter that data set has changed
    }

    // Create view holder for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OutfitViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return OutfitViewHolder(itemView)
    }

    // Bind data to each item in the RecyclerView
    override fun onBindViewHolder(holder: OutfitViewHolder, position: Int) {
        val currentItem = outfitList[position]
        holder.bind(currentItem)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            // Clear the background color of the previously selected item
            if (selectedPosition != RecyclerView.NO_POSITION) {
                notifyItemChanged(selectedPosition)
            }

            // Update the selected position and background color
            selectedPosition = holder.adapterPosition
            it.setBackgroundColor(Color.GRAY)

            // Notify any registered listeners about the item click
            onItemClick(currentItem)
        }

        // Set background color based on the selected position
        if (selectedPosition == holder.adapterPosition) {
            holder.itemView.setBackgroundColor(Color.GRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }
    }


    // Get the selected outfit
    fun getSelectedOutfit(): Garment? {
        return if (selectedPosition != RecyclerView.NO_POSITION) {
            outfits[selectedPosition]
        } else {
            null
        }
    }

    // Return the total number of items in the list
    override fun getItemCount() = outfitList.size

    // Inner class to define the view holder for each item
    inner class OutfitViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.outfitImage)
        private val tvHeading: TextView = itemView.findViewById(R.id.outfitName)

        init {
            // Set click listener for the item view
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    val item = outfitList[position]
                    onItemClick(item) // Pass the clicked item to onItemClick lambda
                }
            }
        }

        // Bind data to the ViewHolder
        fun bind(outfit: RecyclerItem) {
            titleImage.setImageResource(outfit.titleImage)
            tvHeading.text = outfit.heading
        }
    }
}

// Add extension function to OutfitAdapter
fun OutfitAdapter.addShareButtonFunctionality(onShareClick: (RecyclerItem) -> Unit) {
    this.onShareClick = onShareClick
}

// Add a share button click listener
private var onShareClick: (RecyclerItem) -> Unit = {}