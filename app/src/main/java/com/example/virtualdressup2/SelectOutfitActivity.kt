package com.example.virtualdressup2

// SelectOutfitActivity.kt

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.OutfitAdapter
import com.myapp.firebase.users.UserDAO
import com.myapp.revery.Garment
import com.myapp.revery.ReveryAIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SelectOutfitActivity : AppCompatActivity() {

    private lateinit var reveryAIClient: ReveryAIClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var outfitAdapter: OutfitAdapter


    // PLACEHOLDER ****
    private val outfitList = arrayListOf(
        RecyclerItem(R.drawable.female_bottom1, "Outfit 1"),
        RecyclerItem(R.drawable.female_top1, "Outfit 2"),
        RecyclerItem(R.drawable.top1, "Outfit 3"),
        RecyclerItem(R.drawable.outfit4, "Outfit 4"),
        RecyclerItem(R.drawable.outfit5, "Outfit 5"),
        RecyclerItem(R.drawable.outfit6, "Outfit 6"),
        RecyclerItem(R.drawable.outfit1, "Outfit 7"),
        RecyclerItem(R.drawable.outfit2, "Outfit 8"),
        RecyclerItem(R.drawable.outfit3, "Outfit 9"),
        RecyclerItem(R.drawable.outfit4, "Outfit 10"),
        RecyclerItem(R.drawable.outfit5, "Outfit 11"),
        RecyclerItem(R.drawable.outfit6, "Outfit 12")
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_outfit)

        // Initialize ReveryAI client
        reveryAIClient = ReveryAIClient()

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerViewOutfits)
        recyclerView.layoutManager = LinearLayoutManager(this)
        outfitAdapter = OutfitAdapter(outfitList) { onItemClick(it)

        }
        recyclerView.adapter = outfitAdapter

        // Fetch and display initial outfit
        fetchAndDisplayOutfit()

        // Set click listener for the "Save Outfit" button
        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            // Save the selected outfit
            saveSelectedOutfit()
        }
    }

    private fun fetchAndDisplayOutfit() {
        // Make API call to get filtered garments (outfits)
        GlobalScope.launch(Dispatchers.Main) {
            val filteredGarmentsResponse = reveryAIClient.getFilteredGarments()
            if (filteredGarmentsResponse.success) {
                // Update the RecyclerView with the fetched outfits
                outfitAdapter.setItems(filteredGarmentsResponse.garments)
            } else {
                // Handle API call failure
            }
        }
    }

    private fun saveSelectedOutfit() {
        // Get the selected outfit from the adapter
        val selectedOutfit = outfitAdapter.getSelectedOutfit()

        // Check if an outfit is selected
        if (selectedOutfit != null) {
            // Save the selected outfit to the database
            GlobalScope.launch(Dispatchers.IO) {
                val userDAO = UserDAO()
                val outfitId = selectedOutfit.id.toString()
                userDAO.writeDocumentToCollection("outfits", outfitId, selectedOutfit)
                // Display success message
                runOnUiThread {
                    Toast.makeText(this@SelectOutfitActivity, "Outfit Saved", Toast.LENGTH_SHORT).show()
                }
            }
        } else {
            // Show an error message or handle the case where no outfit is selected
            Toast.makeText(this@SelectOutfitActivity, "Outfit failed to save", Toast.LENGTH_SHORT).show()
        }
    }

    private fun onItemClick(position: RecyclerItem) {
        // Handle item click here
        Toast.makeText(this, "Clicked on: ${position.heading}", Toast.LENGTH_SHORT).show()
    }
}