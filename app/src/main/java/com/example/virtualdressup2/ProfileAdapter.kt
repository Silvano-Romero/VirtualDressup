package com.example.virtualdressup2

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.google.firebase.firestore.DocumentSnapshot

class ProfileAdapter(private var profiles: MutableList<DocumentSnapshot>) :
    RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    fun setProfiles(profiles: List<DocumentSnapshot>) {
        this.profiles.clear() // Clear existing profiles
        this.profiles.addAll(profiles) // Add new profiles
        notifyDataSetChanged() // Notify adapter that data set has changed
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_profile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileViewHolder, position: Int) {
        val profile = profiles[position]
        holder.profileNameTextView.text = profile.getString("name") // Adjust according to your data structure
        // Bind other profile information to corresponding views if needed
    }

    override fun getItemCount(): Int {
        return profiles.size
    }

    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val profileNameTextView: TextView = itemView.findViewById(R.id.profileNameTextView)
        // Declare other views here if needed
    }
}