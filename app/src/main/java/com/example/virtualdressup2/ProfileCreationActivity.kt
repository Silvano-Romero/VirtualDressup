package com.example.virtualdressup2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualdressup2.databinding.ProfileCreationBinding
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.users.Profile

class ProfileCreationActivity : AppCompatActivity() {

    private lateinit var firstNameInput: EditText
    private lateinit var binding: ProfileCreationBinding

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
                // Create profile
                val profile = Profile(firstName)
                saveProfileToDatabase(profile) // Implement this method to save profile
                // Create Intent to start ProfileSelectionActivity
                val intent =
                    Intent(this@ProfileCreationActivity, ProfileSelectionActivity::class.java)

                // Put the created profile as an extra
                intent.putExtra("profiles", profile)

                // Start ProfileSelectionActivity
                startActivity(intent)

            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }


    // Save profile to Firestore
    fun saveProfileToDatabase(profile: Profile) {
        val db = FirebaseFirestore.getInstance()
        db.collection("profiles").add(profile)
            .addOnSuccessListener { documentReference ->
                Log.d(TAG, "DocumentSnapshot added with ID: ${documentReference.id}")
            }
            .addOnFailureListener { e ->
                Log.w(TAG, "Error adding document", e)
            }
    }
}




