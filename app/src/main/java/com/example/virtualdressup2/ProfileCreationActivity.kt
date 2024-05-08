package com.example.virtualdressup2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.virtualdressup2.databinding.ProfileCreationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO
import com.myapp.users.Account
import com.myapp.users.User
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class ProfileCreationActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firstNameInput: EditText
    private lateinit var binding: ProfileCreationBinding
    private lateinit var account: Account
    private val profileID = FirebaseAuth.getInstance().currentUser?.uid as String
    private lateinit var avatar: Avatar
    private lateinit var maleModelAdapter: MaleModelAdapter
    private lateinit var femaleModelAdapter: FemaleModelAdapter
    private var maleModelPosition = 0
    private var femaleModelPosition = 0
    private lateinit var model: RecyclerItem
    private var avatarID = "87463ae7-5ced"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val maleModelList = mutableListOf<RecyclerItem>()
        val femaleModelList = mutableListOf<RecyclerItem>()

        binding = ProfileCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize firstNameInput
        firstNameInput = binding.firstNameLayout

        lifecycleScope.launch {
            val avatarID = "87463ae7-5ced"
            val avatar: Avatar =
                AvatarDAO().getSpecificAvatarFromProfile(profileID, avatarID)

            // Handle the avatar object as needed
            val avatarOutfits = avatar.outfits
            // Add outfits to outfitList
            for (outfit in avatarOutfits) {
                val tryOnImgURL =
                    "https://media.revery.ai/revery_client_models/${avatar.modelID}/ou_aligned_transparent.png"
                println("MODEL_FILE_LINK: $tryOnImgURL")
                maleModelList.add(
                    RecyclerItem(
                        R.drawable.outfit1,
                        outfit.outfitID,
                        titleImageURL = tryOnImgURL
                    )
                )
                femaleModelList.add(
                    RecyclerItem(
                        R.drawable.outfit1,
                        outfit.outfitID,
                        titleImageURL = tryOnImgURL
                    )
                )
            }

            // Use LinearLayoutManager to display outfits in a horizontal layout
            val maleLayoutManager =
                LinearLayoutManager(
                    this@ProfileCreationActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.MaleRecyclerView.layoutManager = maleLayoutManager

            // Create an instance of GarmentTopsAdapter and pass in the garmentTopsList and item click listener
            maleModelAdapter = MaleModelAdapter(maleModelList) { _, position ->
                maleModelPosition = position
                // Display a toast message indicating the clicked outfit
                Toast.makeText(
                    this@ProfileCreationActivity,
                    "Male: ${maleModelList[position].titleImageURL}",
                    Toast.LENGTH_LONG
                ).show()
            }

            // Set the adapter for the RecyclerView
            binding.MaleRecyclerView.adapter = maleModelAdapter

            // Use LinearLayoutManager to display outfits in a horizontal layout
            val femaleLayoutManager =
                LinearLayoutManager(
                    this@ProfileCreationActivity,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
            binding.FemaleRecyclerView.layoutManager = femaleLayoutManager

            // Create an instance of GarmentTopsAdapter and pass in the garmentTopsList and item click listener
            femaleModelAdapter = FemaleModelAdapter(femaleModelList) { _, position ->
                femaleModelPosition = position
                // Display a toast message indicating the clicked outfit
                Toast.makeText(
                    this@ProfileCreationActivity,
                    "Female: ${femaleModelList[position].titleImageURL}",
                    Toast.LENGTH_LONG
                ).show()
            }

            // Set the adapter for the RecyclerView
            binding.FemaleRecyclerView.adapter = femaleModelAdapter
        }

        // Set click listener for the back button
        binding.backButton.setOnClickListener {
            val intent = Intent(this@ProfileCreationActivity, ProfileSelectionActivity::class.java)
            startActivity(intent)
            finish()
        }


        // Set click listener for create profile button
        binding.createProfileButton.setOnClickListener {
            // Retrieve input values
            val firstName = firstNameInput.text.toString()

            // Validate input
            if (firstName.isNotEmpty()) {
                // Create avatar
                val avatar = Avatar(profileID, "modelID", firstName, "gender", listOf())

                saveAvatarToDatabase(avatar) // Implement this method to save avatar
                // Create Intent to start ProfileSelectionActivity
                val intent =
                    Intent(this@ProfileCreationActivity, ProfileSelectionActivity::class.java)

                // Put the created avatar as an extra
                intent.putExtra("Avatars", avatar)

                // Start ProfileSelectionActivity
                startActivity(intent)

            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

//    private fun displaySelectedOutfit(outfit: RecyclerItem) {
//        // Update the UI to display the selected outfit details or image
//        // For example, if you have an ImageView to display the outfit image:
//        binding.modelImageView.visibility = View.VISIBLE
//        bindWithModelURL(outfit)
//    }
//
//    private fun bindWithModelURL(outfit: RecyclerItem) {
//        // Set view to urlImage
//        Picasso.get().load(outfit.modelID).into(binding.modelImageView)
//    }

    fun saveAvatarToDatabase(avatar: Avatar) {
        lifecycleScope.launch {
            val avatarDAO = AvatarDAO()
            if(avatarDAO.isProfileInDatabase(avatar.avatarID)){
                avatarDAO.addAvatarToProfile(profileID, avatar)
                println("AVATAR HAS BEEN SAVED: $profileID, ${avatar.avatarID}")
            } else {
                println("Avatar $profileID is not in the database.")
            }
        }
    }

}













