package com.example.virtualdressup2

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.databinding.ActivityGalleryBinding

// Activity class to display a gallery of outfits
class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding

    // List of outfits to display in the gallery
    private val outfitList = arrayListOf(
        RecyclerItem(R.drawable.outfit1, "Outfit 1"),
        RecyclerItem(R.drawable.outfit2, "Outfit 2"),
        RecyclerItem(R.drawable.outfit3, "Outfit 3"),
        RecyclerItem(R.drawable.outfit4, "Outfit 4"),
        RecyclerItem(R.drawable.outfit5, "Outfit 5"),
        RecyclerItem(R.drawable.outfit6, "Outfit 6")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflate the layout using view binding
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Set up click listener for the back button to navigate to the main activity
        binding.backButton.setOnClickListener() {
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Use GridLayoutManager to display outfits in a grid with two columns
        val layoutManager = GridLayoutManager(this, 2)
        binding.galleryRecyclerView.layoutManager = layoutManager

        // Create an instance of GalleryAdapter and pass in the outfitList and item click listener
        val adapter = GalleryAdapter(outfitList) { onItemClick(it) }

        // Set the adapter for the RecyclerView
        binding.galleryRecyclerView.adapter = adapter

        val swipetoDeleteCallback = object : SwipetoDeleteCallback(this) {
            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                when (direction) {
                    ItemTouchHelper.LEFT -> {
                        adapter.deleteItem(viewHolder.adapterPosition)
                    }

                    ItemTouchHelper.RIGHT -> {

                    }
                }
            }
        }
        val touchHelper = ItemTouchHelper(swipetoDeleteCallback)
        touchHelper.attachToRecyclerView(binding.galleryRecyclerView)

    }


    // Function to handle item click events in the RecyclerView
    private fun onItemClick(position: RecyclerItem) {
        // Display a toast message indicating the clicked outfit
        Toast.makeText(this, "Clicked on: ${position.heading}", Toast.LENGTH_SHORT).show()
    }
}
