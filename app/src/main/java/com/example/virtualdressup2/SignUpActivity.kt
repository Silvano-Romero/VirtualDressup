package com.example.virtualdressup2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView

import com.myapp.users.Account
import com.myapp.users.User

class SignUpActivity : AppCompatActivity() {
    private lateinit var firstNameEditText: EditText
    private lateinit var lastNameEditText: EditText
    private lateinit var emailEditText: EditText
    private lateinit var passwordEditText: EditText
    private lateinit var confirmPasswordEditText: EditText
    private lateinit var signUpButton: Button
    private lateinit var signInRedirectTextView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        // Initialize views
        firstNameEditText = findViewById(R.id.firstNameEt)
        lastNameEditText = findViewById(R.id.lastNameEt)
        emailEditText = findViewById(R.id.emailEt)
        passwordEditText = findViewById(R.id.passET)
        confirmPasswordEditText = findViewById(R.id.confirmPassEt)
        signUpButton = findViewById(R.id.sign_up_button)
        signInRedirectTextView = findViewById(R.id.sign_in_redirect)

        // Set click listener for sign up button
        signUpButton.setOnClickListener {
            val firstNameText = firstNameEditText.text.toString()
            val lastNameText = lastNameEditText.text.toString()
            val emailText = emailEditText.text.toString()
            val passwordText = passwordEditText.text.toString()
            val confirmPasswordText = confirmPasswordEditText.text.toString()

            // Create account and user for new member. Update database.
            var user = User(2, firstNameText, lastNameText)
            var account = Account(user, emailText, passwordText)
            user.addUserToDatabase()
            account.addAccountToDatabase()
        }

        // Set click listener for sign in redirect text view
        signInRedirectTextView.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
    }
}
