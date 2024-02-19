// Package declaration specifying the package name for the activity
package com.example.virtualdressup2

// Importing necessary classes and packages
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.virtualdressup2.databinding.ActivityMainBinding
//import com.google.firebase.FirebaseApp
import com.google.firebase.auth.FirebaseAuth

// Declaration of the MainActivity class which extends AppCompatActivity
class MainActivity : AppCompatActivity() {
    // Overriding the onCreate method to initialize the activity

private lateinit var binding: ActivityMainBinding
override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)

    setContentView(binding.root)
    replaceFragment(HomeFragment())

    binding.bottomNavigationView.setOnItemSelectedListener {
        when(it.itemId) {
            R.id.home -> replaceFragment(HomeFragment())
            R.id.profile -> replaceFragment(SettingsFragment())

            else -> {

            }
        }

        true
    }
}

private fun replaceFragment(fragment: Fragment){
    val fragmentManager = supportFragmentManager
    val fragmentTransaction = fragmentManager.beginTransaction()

    fragmentTransaction.replace(R.id.frame_layout,fragment)
    fragmentTransaction.commit()
}
}
