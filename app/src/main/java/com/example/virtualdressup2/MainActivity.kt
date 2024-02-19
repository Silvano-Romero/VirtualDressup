// Package declaration specifying the package name for the activity
package com.example.virtualdressup2

// Importing necessary classes and packages
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import com.example.virtualdressup2.databinding.ActivityMainBinding
//import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

// Declaration of the MainActivity class which extends AppCompatActivity
class MainActivity : AppCompatActivity() {
    // Overriding the onCreate method to initialize the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        supportActionBar?.hide()

        Handler(Looper.getMainLooper()).postDelayed({
            val intent = Intent(this, SignInActivity::class.java)
            startActivity(intent)
            finish()
        }, 3000)
    }
}
