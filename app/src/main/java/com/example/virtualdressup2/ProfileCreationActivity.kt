package com.example.virtualdressup2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myapp.users.Profile
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileCreationActivity : AppCompatActivity() {

    private lateinit var firstNameInput: EditText
    private lateinit var lastNameInput: EditText
    private lateinit var emailInput: EditText
    private lateinit var phoneNumberInput: EditText
    private lateinit var addressInput: EditText
    private lateinit var createProfileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_creation)

        // Initialize views
        firstNameInput = findViewById(R.id.firstNameLayout)
        createProfileButton = findViewById(R.id.createProfileButton)

        // Set click listener for create profile button
        createProfileButton.setOnClickListener {
            // Retrieve input values
            val firstName = firstNameInput.text.toString()

            // Validate input
            if (firstName.isNotEmpty()) {
                // Create profile
                val profile = Profile(firstName)
                // Save profile to database
                saveProfileToDatabase(profile)
            } else {
                Toast.makeText(this, "Please fill in all fields", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun saveProfileToDatabase(profile: Profile) {
        GlobalScope.launch(Dispatchers.Main) {
            profile.saveProfileToDatabase()
            Toast.makeText(this@ProfileCreationActivity, "Profile created successfully", Toast.LENGTH_SHORT).show()
            // Transition back to profile selection activity
            startActivity(Intent(this@ProfileCreationActivity, ProfileSelectionActivity::class.java))
            finish()
        }
    }
}