package com.example.virtualdressup2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.virtualdressup2.databinding.ProfileSelectionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.users.Profile
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ProfileSelectionBinding
    private lateinit var profileAdapter: ProfileAdapter
    private lateinit var firebaseAuth: FirebaseAuth
    private val profileList: MutableList<Profile> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // Retrieve newProfile from intent extras
        val newProfile = intent.getSerializableExtra("profiles") as? Profile

        // Add newProfile to profileList if it's not null
        newProfile?.let { profileList.add(it) }

        // Fetch and display profiles
        fetchProfiles()

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.profileRecyclerview.layoutManager = layoutManager

        // Create an instance of ProfileAdapter and pass in the profileList and item click listener
        profileAdapter = ProfileAdapter(profileList) { profile, _ ->

            // Display a toast message indicating the clicked profile
            Toast.makeText(
                this@ProfileSelectionActivity,
                "Profile: ${profile.firstName}", // Change heading to firstName
                Toast.LENGTH_LONG
            ).show()
            // Create Intent to start the new activity
            val intent = Intent(this@ProfileSelectionActivity, AvatarCreationActivity::class.java)

            // Put the selected profile as an extra
            intent.putExtra("selectedProfile", profile)

            // Start the new activity
            startActivity(intent)
        }
        // Set the adapter for the RecyclerView
        binding.profileRecyclerview.adapter = profileAdapter

        // Set click listener for "Create New Profile" button
        binding.fabAdd.setOnClickListener {
            // Start ProfileCreationActivity
            val intent = Intent(this, ProfileCreationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun fetchProfiles() {
        val db = FirebaseFirestore.getInstance()
        db.collection("profiles")
            .get()
            .addOnSuccessListener { documents ->
                for (document in documents) {
                    val profile = document.toObject(Profile::class.java)
                    // Only add the profile to profileList if it's not already in the list
                    if (profile !in profileList) {
                        profileList.add(profile)
                    }
                    Log.e(TAG, "fetchProfiles: ${profileList.joinToString()}")
                }
                profileAdapter.notifyDataSetChanged() // Notify the adapter that the data set has changed
            }
            .addOnFailureListener { exception ->
                Log.w(TAG, "Error getting documents: ", exception)
            }
    }


}
