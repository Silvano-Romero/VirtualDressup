package com.example.virtualdressup2

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.myapp.firebase.users.UserDAO
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class ProfileSelectionActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView
    private lateinit var adapter: ProfileAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_selection)

        recyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Initialize adapter
        adapter = ProfileAdapter(mutableListOf())
        recyclerView.adapter = adapter

        // Fetch and display profiles
        fetchProfiles()
    }

    private fun fetchProfiles() {
        GlobalScope.launch(Dispatchers.Main) {
            val userDAO = UserDAO()
            val profiles = userDAO.getAllDocumentsFromCollection("users") // Assuming "users" is your collection name
            adapter.setProfiles(profiles)
        }
    }
}