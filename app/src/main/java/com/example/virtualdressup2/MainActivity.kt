// Package declaration specifying the package name for the activity
package com.example.virtualdressup2

// Importing necessary classes and packages
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import com.example.virtualdressup2.databinding.ActivityMainBinding
//import com.google.firebase.FirebaseApp

// Declaration of the MainActivity class which extends AppCompatActivity
class MainActivity : AppCompatActivity() {
    // Overriding the onCreate method to initialize the activity

    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)
        replaceFragment(HomeFragment())

        binding.galleryButton.setOnClickListener {
            // Start the activity you want to navigate to
            val intent = Intent(this, GalleryActivity::class.java)
            startActivity(intent)
        }

        binding.outfitButton.setOnClickListener {
            val intent = Intent(this, OutfitActivity::class.java)
            startActivity(intent)
        }

        binding.calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }

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
