package com.example.virtualdressup2

// SelectOutfitActivity.kt

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.OutfitAdapter
import com.example.virtualdressup2.databinding.ActivityGalleryBinding
import com.example.virtualdressup2.databinding.SelectOutfitBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO
import com.myapp.firebase.users.UserDAO
import com.myapp.revery.Garment
import com.myapp.revery.ReveryAIClient
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class SelectOutfitActivity : AppCompatActivity() {

    //    private lateinit var reveryAIClient: ReveryAIClient
//    private lateinit var recyclerView: RecyclerView
//    private lateinit var newOutfitAdapter: NewOutfitAdapter
//    private var mostRecentPosition: Int = 0
    private lateinit var binding: SelectOutfitBinding
    private lateinit var model: RecyclerItem
//    private lateinit var tops: RecyclerItem
//    private lateinit var bottoms: RecyclerItem
    private lateinit var topsAdapter: GarmentTopsAdapter
    private lateinit var bottomsAdapter: GarmentBottomsAdapter

//    private val outfitList = mutableListOf<RecyclerItem>()


    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val garmentTopsList = mutableListOf<RecyclerItem>()
        val garmentBottomsList = mutableListOf<RecyclerItem>()
        binding = SelectOutfitBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            try {
                val avatarID = "Avatar01"
                val profileID = "YHd6kmErjjgSoQr9QxgIwA0sUGW2"
                val avatar: Avatar = AvatarDAO().getSpecificAvatarFromProfile(profileID, avatarID)
                val avatarModels = avatar.modelID

                // Assuming `model` contains the model URL
                val modelURL =
                    "https://media.revery.ai/revery_client_models/${avatar.modelID}/ou_aligned_transparent.png"
                model = RecyclerItem(
                    titleImage = 0, // Set to 0 or any other default value since we're using titleImageURL
                    heading = "", // Add a heading if necessary
                    modelID = modelURL // Assign modelURL to modelID
                )
//                // Add outfits to outfitList
                for (avatar in avatarModels) {
                    val topsURL =
                        "https://revery-integration-tools.s3.us-east-2.amazonaws.com/API_website/tops.jpeg"
                    garmentTopsList.add(RecyclerItem(0, "", titleImageURL = topsURL))

                }

                for (avatar in avatarModels) {
                    val bottomsURL =
                        "https://revery-integration-tools.s3.us-east-2.amazonaws.com/API_website/bottoms.jpeg"
                    garmentBottomsList.add(RecyclerItem(0, "", titleImageURL = bottomsURL))
                }

                // Load the image into the ImageView using Picasso
                bindWithModelURL(model)
                displaySelectedOutfit(model)

                // Use LinearLayoutManager to display outfits in a horizontal layout
                val topsLayoutManager = LinearLayoutManager(this@SelectOutfitActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.topsRecyclerview.layoutManager = topsLayoutManager

                // Create an instance of GarmentTopsAdapter and pass in the garmentTopsList and item click listener
                topsAdapter = GarmentTopsAdapter(garmentTopsList) { _, position ->
                    // Display a toast message indicating the clicked outfit
                    Toast.makeText(
                        this@SelectOutfitActivity,
                        "Top: ${garmentTopsList[position].titleImageURL}",
                        Toast.LENGTH_LONG
                    ).show()
                }

                // Set the adapter for the RecyclerView
                binding.topsRecyclerview.adapter = topsAdapter

                val bottomsLayoutManager = LinearLayoutManager(this@SelectOutfitActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.bottomsRecyclerView.layoutManager = bottomsLayoutManager

                // Create an instance of GarmentBottomsAdapter and pass in the garmentBottomsList and item click listener
                bottomsAdapter = GarmentBottomsAdapter(garmentBottomsList) { _, position ->
                    // Display a toast message indicating the clicked outfit
                    Toast.makeText(
                        this@SelectOutfitActivity,
                        "Bottom: ${garmentBottomsList[position].titleImageURL}",
                        Toast.LENGTH_LONG
                    ).show()
                }
                binding.bottomsRecyclerView.adapter = bottomsAdapter

            } catch (e: Exception) {
                Toast.makeText(
                    this@SelectOutfitActivity,
                    "Error loading model image: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }

        }

        // Fetch and display initial outfit
            // fetchAndDisplayOutfit()

            // Set click listener for the back button
        binding.backButton.setOnClickListener {
            onBackPressed()
        }

        binding.buttonSave.setOnClickListener{

        }

        binding.buttonTryOn.setOnClickListener{

        }

            // Display TryOnFragment
//        supportFragmentManager.beginTransaction().replace(R.id.tryOnFragmentContainer, TryOnFragment()).commit()
        }

    private fun displaySelectedOutfit(outfit: RecyclerItem) {
        // Update the UI to display the selected outfit details or image
        // For example, if you have an ImageView to display the outfit image:
        binding.modelImageView.visibility = View.VISIBLE
        bindWithModelURL(outfit)
    }

    private fun bindWithModelURL(outfit: RecyclerItem) {
        // Set view to urlImage
        Picasso.get().load(outfit.modelID).into(binding.modelImageView)
    }


//        fun fetchAndDisplayOutfit() {
//            // Make API call to get filtered garments (outfits)
//            GlobalScope.launch(Dispatchers.Main) {
//                val filteredGarmentsResponse = reveryAIClient.getFilteredGarments()
//                if (filteredGarmentsResponse.success) {
//                    // Update the RecyclerView with the fetched outfits
//                    outfitAdapter.setItems(filteredGarmentsResponse.garments)
//                } else {
//                    // Handle API call failure
//                }
//            }
//        }

//    fun saveSelectedOutfit() {
//        val selectedPosition = mostRecentPosition
//        // Get the selected outfit from the adapter
//        val selectedOutfit = outfitAdapter.getItemAtPosition(mostRecentPosition)
//
//        // Check if an outfit is selected
//        if (selectedOutfit != null) {
//            // Save the selected outfit to the database
//            GlobalScope.launch(Dispatchers.IO) {
//                val userDAO = UserDAO()
//                userDAO.writeDocumentToCollection("outfits", selectedOutfit.heading, selectedOutfit)
//                // Display success message
//                runOnUiThread {
//                    Toast.makeText(this@SelectOutfitActivity, "Outfit Saved", Toast.LENGTH_SHORT).show()
//                }
//            }
//        } else {
//            // Show an error message or handle the case where no outfit is selected
//            Toast.makeText(this@SelectOutfitActivity, "Please select an outfit", Toast.LENGTH_SHORT).show()
//        }
//    }


        fun onItemClick(position: RecyclerItem) {
            // Handle item click here
            Toast.makeText(this, "Clicked on: ${position.heading}", Toast.LENGTH_SHORT).show()
        }
    }
