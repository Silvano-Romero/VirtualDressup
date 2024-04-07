package com.example.virtualdressup2

import android.content.ClipData.Item
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.databinding.ActivityGalleryBinding
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO
import kotlinx.coroutines.launch

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
        val outfitList = mutableListOf<RecyclerItem>()

        lifecycleScope.launch {
            val avatarID = "Avatar01"
            val profileID = "YHd6kmErjjgSoQr9QxgIwA0sUGW2"
            val avatar: Avatar =
                AvatarDAO().getSpecificAvatarFromProfile(profileID, avatarID)

            // Handle the avatar object as needed
            val avatarOutfits = avatar.outfits

            // Add outfits to outfitList
            for (outfit in avatarOutfits) {
                outfitList.add(RecyclerItem(R.drawable.outfit1, outfit.outfitID))
            }

            // Inflate the layout using view binding
            binding = ActivityGalleryBinding.inflate(layoutInflater)
            setContentView(binding.root)

            // Set up click listener for the back button to navigate to the main activity
            binding.backButton.setOnClickListener() {
                val intent = Intent(this@GalleryActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            // Use GridLayoutManager to display outfits in a grid with two columns
            val layoutManager = GridLayoutManager(this@GalleryActivity, 2)

            binding.galleryRecyclerView.layoutManager = layoutManager

            // Create an instance of GalleryAdapter and pass in the outfitList and item click listener
            val adapter = GalleryAdapter(outfitList) { _, position ->
                // Display a toast message indicating the clicked outfit
                Toast.makeText(
                    this@GalleryActivity,
                    "Outfit Details\nTop: ${avatarOutfits[position].top}\nBottom: ${avatarOutfits[position].bottom}",
                    Toast.LENGTH_LONG
                ).show()
            }
            // Set the adapter for the RecyclerView
            binding.galleryRecyclerView.adapter = adapter

            // Set up click listener for the back button to navigate to the main activity
            binding.backButton.setOnClickListener() {
                val intent = Intent(this@GalleryActivity, MainActivity::class.java)
                startActivity(intent)
                finish()
            }

            // Set the adapter for the RecyclerView
            binding.galleryRecyclerView.adapter = adapter
            val swipetoDeleteCallback = object : SwipetoDeleteCallback(this@GalleryActivity) {
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



    }


    // Function to handle item click events in the RecyclerView
    private fun onItemClick(position: RecyclerItem) {
        // Display a toast message indicating the clicked outfit
        Toast.makeText(this, "Clicked on: ${position.heading}", Toast.LENGTH_SHORT).show()
    }
}
