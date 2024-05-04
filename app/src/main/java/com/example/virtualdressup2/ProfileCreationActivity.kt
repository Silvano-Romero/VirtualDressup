package com.example.virtualdressup2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.example.virtualdressup2.databinding.ProfileCreationBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO
import com.myapp.users.Account
import com.myapp.users.User
import kotlinx.coroutines.launch

class ProfileCreationActivity : AppCompatActivity() {

    private lateinit var firebaseAuth: FirebaseAuth
    private lateinit var firstNameInput: EditText
    private lateinit var binding: ProfileCreationBinding
    private lateinit var account: Account
    private val profileID = FirebaseAuth.getInstance().currentUser?.uid as String
    private lateinit var avatar: Avatar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileCreationBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initialize firstNameInput
        firstNameInput = binding.firstNameLayout

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













