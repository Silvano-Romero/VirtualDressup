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

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.select_outfit)

        // Initialize ReveryAI client
        reveryAIClient = ReveryAIClient()

        // Initialize RecyclerView and adapter
        recyclerView = findViewById(R.id.recyclerViewOutfits)
        recyclerView.layoutManager = LinearLayoutManager(this)
        outfitAdapter = OutfitAdapter()
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
}