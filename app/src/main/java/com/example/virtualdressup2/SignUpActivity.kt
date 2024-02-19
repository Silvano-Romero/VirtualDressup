// Package declaration specifying the package name for the activity
package com.example.virtualdressup2

// Importing necessary classes and packages
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualdressup2.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.myapp.users.Account
import com.myapp.users.User

// Declaration of the SignUpActivity class which extends AppCompatActivity
class SignUpActivity : AppCompatActivity() {
    // Declaring variables for view binding and Firebase Authentication
    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    // Overriding the onCreate method to initialize the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Inflating the layout using view binding
        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Initializing Firebase Authentication instance
        firebaseAuth = FirebaseAuth.getInstance()

        // Setting click listener for the sign-in redirection button
        binding.signInRedirect.setOnClickListener {
            // Creating an Intent to navigate to SignInActivity
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }

        // Setting click listener for the sign-up button
        binding.signUpButton.setOnClickListener {
            // Retrieving user input from EditText fields
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val firstNameText = binding.firstNameEt.text.toString()
            val lastNameText = binding.lastNameEt.text.toString()

            // Checking if email, password, and confirm password are not empty
            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                // Checking if password and confirm password match
                if (pass == confirmPass) {
                    // Attempting to create a new user account with the provided credentials
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { signUpTask ->
                        if (signUpTask.isSuccessful) {
                            // Sign-up successful, creating User and Account objects and adding them to the database
                            val firebaseUser = firebaseAuth.currentUser
                            val user = User(firstNameText, lastNameText, firebaseUser?.uid ?: "")
                            val account = Account(user, email, pass)
                            user.addUserToDatabase() // This will use Firebase auto-generated user ID
                            account.addAccountToDatabase()
                            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                            firebaseAuth.signOut()
                            // Navigating to SignInActivity after sign-up
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        } else {
                            // Sign-up failed, displaying error message
                            Toast.makeText(this, signUpTask.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    // Password and confirm password do not match, displaying error message
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                // One or more fields are empty, displaying error message
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
