package com.example.virtualdressup2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.myapp.firebase.users.UserDAO

class ProfileCreationActivity : AppCompatActivity() {

    private lateinit var nameEditText: EditText
    private lateinit var ageEditText: EditText
    private lateinit var createProfileButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_creation)

        nameEditText = findViewById(R.id.editTextName)
        ageEditText = findViewById(R.id.editTextAge)
        createProfileButton = findViewById(R.id.buttonCreateProfile)

        createProfileButton.setOnClickListener {
            val name = nameEditText.text.toString()
            val age = ageEditText.text.toString().toIntOrNull()

            if (name.isNotEmpty() && age != null) {
                // Save profile data to Firebase or perform any required action
                saveProfileToDatabase(name, age)
            } else {
                // Display error message if fields are empty or age is not valid
                // You can customize this based on your app's requirements
                // For simplicity, a toast message is shown here
                showToast("Please enter valid name and age")
            }
        }
    }

    private fun saveProfileToDatabase(name: String, age: Int) {
        // Here you would save the profile data to your Firebase database using your UserDAO or any other method
        // For demonstration purposes, we'll just show a toast message
        showToast("Profile created successfully!")
        // You can also navigate back to previous activity or perform any other action here
        // For example:
        // onBackPressed()
        // finish()
    }

    private fun showToast(message: String) {
        // Utility function to display toast messages
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
    }
}