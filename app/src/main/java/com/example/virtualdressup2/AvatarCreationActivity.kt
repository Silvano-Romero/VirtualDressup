package com.example.virtualdressup2

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.virtualdressup2.databinding.AvatarCreationBinding
import com.google.firebase.auth.FirebaseAuth
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO
import com.squareup.picasso.Picasso
import kotlinx.coroutines.launch

class AvatarCreationActivity : AppCompatActivity() {
    private lateinit var binding: AvatarCreationBinding
    private var avatar: Avatar = Avatar()
    private lateinit var model: RecyclerItem
    private val profileID = FirebaseAuth.getInstance().currentUser?.uid as String
    private var avatarID = "87463ae7-5ced"
    var selectedAvatar: ImageView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = AvatarCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.backButton.setOnClickListener {
            val intent = Intent(this@AvatarCreationActivity, ProfileSelectionActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.submitButton.setOnClickListener{
            val intent = Intent(this@AvatarCreationActivity, MainActivity::class.java)
            startActivity(intent)
            finish()
        }

        binding.maleAvatarImage.setOnClickListener {
            // If another avatar was previously selected, reset its background
            selectedAvatar?.setBackgroundColor(Color.TRANSPARENT)

            // Update the selected avatar and change its background
            selectedAvatar = it as ImageView
            selectedAvatar?.setBackgroundColor(Color.GRAY)
        }

        binding.femaleAvatarImage.setOnClickListener {
            // If another avatar was previously selected, reset its background
            selectedAvatar?.setBackgroundColor(Color.TRANSPARENT)

            // Update the selected avatar and change its background
            selectedAvatar = it as ImageView
            selectedAvatar?.setBackgroundColor(Color.GRAY)
        }

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

                // Load the image into the ImageView using Picasso
                bindWithModelURL(model)
                displayMaleAvatar(model)

            } catch (e: Exception) {
                Toast.makeText(
                    this@AvatarCreationActivity,
                    "Error loading avatar image: ${e.message}",
                    Toast.LENGTH_SHORT
                ).show()
            }
        }
    }
            private fun displayMaleAvatar(outfit: RecyclerItem) {
                // Update the UI to display the selected outfit details or image
                // For example, if you have an ImageView to display the outfit image:
                binding.maleAvatarImage.visibility = View.VISIBLE
                binding.femaleAvatarImage.visibility = View.VISIBLE
                bindWithModelURL(outfit)
            }

    private fun bindWithModelURL(outfit: RecyclerItem) {
        // Set view to urlImage
        Picasso.get().load(outfit.modelID).into(binding.maleAvatarImage)
        Picasso.get().load(outfit.modelID).into(binding.femaleAvatarImage)

    }



}