package com.example.virtualdressup2

import com.myapp.revery.ReveryAIClient
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
import com.myapp.revery.ReveryAIConstants
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
    private var isMale = false
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
//            val avatarOutfits = avatar.outfits
//            val avatarModels = avatar.gender
            val fetchMaleModel = ReveryAIClient().getModels(gender = ReveryAIConstants.MALE)
            val fetchFemaleModel = ReveryAIClient().getModels(gender = ReveryAIConstants.FEMALE)

//            println("fetchMaleModel.model_ids: ${fetchMaleModel.model_ids}")
//            println("fetchFemaleModel.model_ids: ${fetchFemaleModel.model_ids}")

            for (modelId in fetchMaleModel.model_ids) {
                val tryOnImgURL = "https://media.revery.ai/revery_client_models/$modelId/ou_aligned_transparent.png"
                maleModelList.add(
                    RecyclerItem(
                        0,
                        "",
                        titleImageURL = tryOnImgURL,
                        modelID = modelId
                    )
                )
            }

            for (modelId in fetchFemaleModel.model_ids) {
                val tryOnImgURL = "https://media.revery.ai/revery_client_models/$modelId/ou_aligned_transparent.png"
                femaleModelList.add(
                    RecyclerItem(
                        0,
                        "",
                        titleImageURL = tryOnImgURL,
                        modelID = modelId
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
                isMale = true
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
                isMale = false
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
                var modelID = ""
                var gender = ""
                if(isMale){
                    modelID = maleModelList[maleModelPosition].modelID as String
                    gender = ReveryAIConstants.MALE
                }else{
                    modelID = femaleModelList[femaleModelPosition].modelID as String
                    gender = ReveryAIConstants.FEMALE
                }

                val avatar = Avatar(profileID, modelID, firstName, gender, listOf())
                //println("AVATAR_TO_PROFILE: $avatar")
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

    fun saveAvatarToDatabase(avatar: Avatar) {
        lifecycleScope.launch {
            val avatarDAO = AvatarDAO()
            if (avatarDAO.isProfileInDatabase(avatar.avatarID)) {
                avatarDAO.addAvatarToProfile(profileID, avatar)
                println("AVATAR HAS BEEN SAVED: $profileID, ${avatar.avatarID}")
            } else {
                println("Avatar $profileID is not in the database.")
            }
        }

    }
}













