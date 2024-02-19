// Package declaration specifying the package name for the activity
package com.example.virtualdressup2

// Importing necessary classes and packages
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.content.Intent
import android.os.Handler
import android.os.Looper
import android.view.WindowManager
import androidx.core.os.postDelayed

// Declaration of the SplashScreen class which extends AppCompatActivity
class SplashScreen : AppCompatActivity() {
    // Overriding the onCreate method to initialize the activity
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        // Setting the layout for the activity from the XML layout resource file
        setContentView(R.layout.splash_screen)

        // Remove the status bar for a fullscreen experience
        window.setFlags(
            WindowManager.LayoutParams.FLAG_FULLSCREEN,
            WindowManager.LayoutParams.FLAG_FULLSCREEN
        )
    }

    // Overriding the onStart method which is called when the activity becomes visible to the user
    override fun onStart() {
        super.onStart()
        // Creating a Handler object to delay the execution of code
        Handler(Looper.getMainLooper()).postDelayed(2000){
            // Creating an Intent to start the SignInActivity
            val intent = Intent(this, SignInActivity::class.java)
            // Starting the SignInActivity
            startActivity(intent)
            finish()
        }
    }
}
