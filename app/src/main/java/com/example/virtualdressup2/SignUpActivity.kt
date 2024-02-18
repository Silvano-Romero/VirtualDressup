package com.example.virtualdressup2

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.virtualdressup2.databinding.ActivitySignUpBinding
import com.google.firebase.auth.FirebaseAuth
import com.myapp.users.Account
import com.myapp.users.User

class SignUpActivity : AppCompatActivity() {

    private lateinit var binding: ActivitySignUpBinding
    private lateinit var firebaseAuth: FirebaseAuth

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivitySignUpBinding.inflate(layoutInflater)
        setContentView(binding.root)

        firebaseAuth = FirebaseAuth.getInstance()

        binding.signInRedirect.setOnClickListener {
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
        }
        binding.signUpButton.setOnClickListener {
            val email = binding.emailEt.text.toString()
            val pass = binding.passET.text.toString()
            val confirmPass = binding.confirmPassEt.text.toString()
            val firstNameText = binding.firstNameEt.text.toString()
            val lastNameText = binding.lastNameEt.text.toString()

            if (email.isNotEmpty() && pass.isNotEmpty() && confirmPass.isNotEmpty()) {
                if (pass == confirmPass) {
                    firebaseAuth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener { signUpTask ->
                        if (signUpTask.isSuccessful) {
                            val firebaseUser = firebaseAuth.currentUser
                            val user = User(firstNameText, lastNameText, firebaseUser?.uid ?: "")
                            val account = Account(user, email, pass)
                            user.addUserToDatabase() // This will use Firebase auto-generated user ID
                            account.addAccountToDatabase()
                            Toast.makeText(this, "Sign up successful", Toast.LENGTH_SHORT).show()
                            firebaseAuth.signOut()
                            val intent = Intent(this, SignInActivity::class.java)
                            startActivity(intent)
                        } else {
                            Toast.makeText(this, signUpTask.exception?.message, Toast.LENGTH_SHORT).show()
                        }
                    }
                } else {
                    Toast.makeText(this, "Password is not matching", Toast.LENGTH_SHORT).show()
                }
            } else {
                Toast.makeText(this, "Empty Fields Are not Allowed !!", Toast.LENGTH_SHORT).show()
            }
        }
    }
}
