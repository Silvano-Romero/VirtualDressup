package com.example.virtualdressup2

import android.content.ContentValues.TAG
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.virtualdressup2.databinding.ProfileSelectionBinding
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.Avatar
import com.myapp.firebase.revery.AvatarDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileSelectionActivity : AppCompatActivity() {
    private lateinit var binding: ProfileSelectionBinding
    private lateinit var avatarAdapter: AvatarAdapter
    private val avatarList: MutableList<Avatar> = mutableListOf()
    private val profileID = FirebaseAuth.getInstance().currentUser?.uid as String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ProfileSelectionBinding.inflate(layoutInflater)
        setContentView(binding.root)

        lifecycleScope.launch {
            val avatarDAO = AvatarDAO()
            avatarList.addAll(avatarDAO.getAvatarsFromProfile(profileID))
            avatarAdapter.notifyDataSetChanged()

        }

        val layoutManager = StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL)
        binding.profileRecyclerview.layoutManager = layoutManager

        // Create an instance of ProfileAdapter and pass in the profileList and item click listener
        avatarAdapter = AvatarAdapter(avatarList) { profile, position ->
            var avatar = avatarList[position]

            CurrentProfile.profileID = avatar.avatarID
            CurrentProfile.gender = avatar.gender
            println("SELECTED_PROFILE: ${CurrentProfile.profileID}, ${CurrentProfile.gender}")
            // Display a toast message indicating the clicked profile
            Toast.makeText(
                this@ProfileSelectionActivity,
                "Profile: ${profile.firstName}", // Change heading to firstName
                Toast.LENGTH_LONG
            ).show()
            // Create Intent to start the new activity
            val intent = Intent(this@ProfileSelectionActivity, MainActivity::class.java)

            // Put the selected profile as an extra
//            intent.putExtra("Avatars", profile)
            println("PROFILE_SELECT_DETAILS: ${CurrentProfile.details()}")
            // Start the new activity
            startActivity(intent)
        }
        // Set the adapter for the RecyclerView
        binding.profileRecyclerview.adapter = avatarAdapter

        // Set click listener for "Create New Profile" button
        binding.fabAdd.setOnClickListener {
            // Start ProfileCreationActivity
            val intent = Intent(this, ProfileCreationActivity::class.java)
            startActivity(intent)
            finish()
        }
    }


}
