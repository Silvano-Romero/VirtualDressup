package com.example.virtualdressup2

// SelectOutfitActivity.kt

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils.replace
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.lifecycle.LifecycleCoroutineScope
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.virtualdressup2.OutfitAdapter
import com.example.virtualdressup2.databinding.ActivityGalleryBinding
import com.example.virtualdressup2.databinding.SelectOutfitBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.firebase.auth.FirebaseAuth
import com.myapp.firebase.Avatar
import com.myapp.firebase.Outfit
import com.myapp.firebase.revery.AvatarDAO
import com.myapp.firebase.users.UserDAO
import com.myapp.revery.Garment
import com.myapp.revery.ReveryAIClient
import com.myapp.revery.ReveryAIConstants
import com.myapp.revery.TryOnResponse
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
    private var topGarmentPosition = 0
    private var bottomGarmentPosition = 0
    private var avatarID = CurrentProfile.profileID
    private val profileID = FirebaseAuth.getInstance().currentUser?.uid as String
    private var tryOnResponse: TryOnResponse? = null
    private var avatar: Avatar = Avatar()
    private var outfit: Outfit = Outfit()

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
                avatar = AvatarDAO().getSpecificAvatarFromProfile(profileID, avatarID)
                val avatarModels = avatar.modelID

                // Assuming `model` contains the model URL
                val modelURL =
                    "https://media.revery.ai/revery_client_models/${avatar.modelID}/ou_aligned_transparent.png"
                model = RecyclerItem(
                    titleImage = 0, // Set to 0 or any other default value since we're using titleImageURL
                    heading = "", // Add a heading if necessary
                    modelID = modelURL // Assign modelURL to modelID
                )
                // Add outfits to outfitList
                println("OUTFIT_SELECT: ${CurrentProfile.details()}")
                val filteredGarmentsResponse = ReveryAIClient().getFilteredGarments(gender = CurrentProfile.gender, category = ReveryAIConstants.TOPS)
                for (top in filteredGarmentsResponse.garments) {
                    var topsUrl: String = top.imageUrls.productImage.replace("\"", "")
                    var topID = top.id.replace("\"", "")
                    garmentTopsList.add(RecyclerItem(0, "", titleImageURL = topsUrl, topID = topID))
                }

                val bottoms = ReveryAIClient().getFilteredGarments(gender = CurrentProfile.gender, category = ReveryAIConstants.BOTTOMS)
                for (bottom in bottoms.garments) {
                    val bottomsURL = bottom.imageUrls.productImage.replace("\"", "")
                    var bottomID = bottom.id.replace("\"", "")
                    garmentBottomsList.add(RecyclerItem(0, "", titleImageURL = bottomsURL, BottomID = bottomID))
                }

                // Load the image into the ImageView using Picasso
                bindWithModelURL(model)
                displaySelectedOutfit(model)

                // Use LinearLayoutManager to display outfits in a horizontal layout
                val topsLayoutManager = LinearLayoutManager(this@SelectOutfitActivity, LinearLayoutManager.HORIZONTAL, false)
                binding.topsRecyclerview.layoutManager = topsLayoutManager

                // Create an instance of GarmentTopsAdapter and pass in the garmentTopsList and item click listener
                topsAdapter = GarmentTopsAdapter(garmentTopsList) { _, position ->
                    topGarmentPosition = position
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
                    bottomGarmentPosition = position
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
            val intent = Intent(this@SelectOutfitActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.buttonSave.setOnClickListener{
            // Save outfit to avatar in firestone if there is a valid tryon response value.
            lifecycleScope.launch {
                if( tryOnResponse !=  null){
                    AvatarDAO().addOutfitToAvatar(profileID, avatar.avatarID ,outfit)
                    println("OUTFIT HAS BEEN SAVED: $profileID, ${avatar.avatarID}, $outfit")
                }
                tryOnResponse = null
            }

        }

        binding.buttonTryOn.setOnClickListener{
            //
            lifecycleScope.launch {
                avatar = AvatarDAO().getSpecificAvatarFromProfile(profileID , avatarID)
                val topID = garmentTopsList[topGarmentPosition].topID as String
                val bottomID = garmentBottomsList[bottomGarmentPosition].BottomID as String

                val garments = mapOf(
                    "tops" to topID,
                    "bottoms" to bottomID
                )
                // Makes API requestTryOn with provided parameters
                tryOnResponse = ReveryAIClient().requestTryOn(garments, avatar.modelID, null, "transparent", false)
                val modelFile = tryOnResponse?.modelMetadata?.modelFile as String

                // Update model image with outfit in application
                outfit = Outfit(topID, bottomID, modelFile)
                model = RecyclerItem(
                    titleImage = 0, // Set to 0 or any other default value since we're using titleImageURL
                    heading = "", // Add a heading if necessary
                    modelID = "https://media.revery.ai/generated_model_image/${modelFile}.png" // Assign modelURL to modelID
                )
                bindWithModelURL(model)
                displaySelectedOutfit(model)
            }
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
