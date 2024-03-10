package com.example.virtualdressup2

// SelectOutfitActivity.kt

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.OutfitAdapter
import com.example.virtualdressup2.R
import com.myapp.firebase.users.UserDAO
import com.myapp.revery.FilteredGarmentsResponse
import com.myapp.revery.Garment
import com.myapp.revery.ReveryAIClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class SelectOutfitActivity : AppCompatActivity() {

    private lateinit var reveryAIClient: ReveryAIClient
    private lateinit var recyclerView: RecyclerView
    private lateinit var outfitAdapter: OutfitAdapter

    @SuppressLint("MissingInflatedId")
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

        // Set click listener for the button to select a new outfit
        findViewById<Button>(R.id.buttonSave).setOnClickListener {
            // Fetch and display a new outfit when the button is clicked
            fetchAndDisplayOutfit()
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
        val UserDAO = UserDAO()

        // Check if an outfit is selected
        if (selectedOutfit != null) {
            // Save the selected outfit to the database
            GlobalScope.launch(Dispatchers.IO) {
                UserDAO.writeDocumentToCollection("outfits", selectedOutfit.id.toString(), selectedOutfit)
            }
        } else {
            // Show an error message or handle the case where no outfit is selected
        }
    }
}