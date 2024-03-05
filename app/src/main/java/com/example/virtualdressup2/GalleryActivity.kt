package com.example.virtualdressup2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.example.virtualdressup2.databinding.ActivityGalleryBinding

class GalleryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityGalleryBinding

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
        binding = ActivityGalleryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener(){
            val intent = Intent(this, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        // Use GridLayoutManager with span count 2 for two columns
        val layoutManager = GridLayoutManager(this, 2)
        binding.galleryRecyclerView.layoutManager = layoutManager

        // Create an instance of GalleryAdapter and pass in the outfitList and item click listener
        val adapter = GalleryAdapter(outfitList) { onItemClick(it) }

        // Set the adapter for the RecyclerView
        binding.galleryRecyclerView.adapter = adapter
    }

    private fun onItemClick(position: RecyclerItem) {
        // Handle item click here
        Toast.makeText(this, "Clicked on: ${position.heading}", Toast.LENGTH_SHORT).show()
    }
}
