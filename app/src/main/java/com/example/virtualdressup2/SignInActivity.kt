// Package declaration specifying the package name for the activity
package com.example.virtualdressup2

// Importing necessary classes and packages
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualdressup2.databinding.ActivitySignInBinding
import com.google.firebase.auth.FirebaseAuth

// Declaration of the SignInActivity class which extends AppCompatActivity
class SignInActivity : AppCompatActivity() {

    // Declaring variables for view binding and Firebase Authentication
    private lateinit var binding: ActivitySignInBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // Overriding the onCreate method to initialize the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Inflating the layout using view binding
        binding = ActivitySignInBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing Firebase Authentication instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Setting click listener for the sign-up button
        binding.signUpButton.setOnClickListener {
            // Creating an Intent to navigate to SignUpActivity
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }

        // Setting click listener for the login button
        binding.loginButton.setOnClickListener {
            // Retrieving email and password from input fields
            val email = binding.usernameInput.text.toString()
            val pass = binding.passwordInput.text.toString()

            // Checking if email and password are not empty
            if (email.isNotEmpty() && pass.isNotEmpty()) {
                // Attempting to sign in with the provided credentials
                firebaseAuth.signInWithEmailAndPassword(email, pass)
                    .addOnCompleteListener { signInTask ->
                        if (signInTask.isSuccessful) {
                            // Sign-in successful, navigate to MainActivity
                            Toast.makeText(this, "Successful Sign In!", Toast.LENGTH_SHORT).show()
                            val intent = Intent(this, MainActivity::class.java)
                            startActivity(intent)
                            finish()
                        } else {
                            // Sign-in failed, display error message
                            Toast.makeText(this, "Invalid email or password", Toast.LENGTH_SHORT)
                                .show()
                        }
                    }
            } else {
                // Displaying a toast message for empty fields
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }

    // Overriding the onStart method
    // This method checks if the user is already signed in and navigates to MainActivity if so
//    override fun onStart() {
//        super.onStart()
//
//        if(firebaseAuth.currentUser != null){
//            val intent = Intent(this, AvatarCreationActivity::class.java)
//            startActivity(intent)
//        }
//    }
}
