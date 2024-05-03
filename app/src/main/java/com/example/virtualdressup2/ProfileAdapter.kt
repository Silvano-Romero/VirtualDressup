package com.example.virtualdressup2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapp.users.Profile

class ProfileAdapter(
    private val profileList: MutableList<Profile>, // List of profiles to display
    private val onItemClick: (Profile, position: Int) -> Unit // Callback function for item click events
) : RecyclerView.Adapter<ProfileAdapter.ProfileViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ProfileViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return ProfileViewHolder(view)
    }

    override fun onBindViewHolder(holder: ProfileAdapter.ProfileViewHolder, position: Int) {
        val profile = profileList[position]
        holder.bind(profile)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            // Update the selected position and invoke onItemClick callback
            selectedPosition = position
            onItemClick(profile, position)
            notifyDataSetChanged() // Update the view to reflect the selection change
        }

        // Set background color based on the selected position
        if (selectedPosition == holder.adapterPosition) {
            holder.itemView.setBackgroundColor(Color.GRAY)
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT)
        }

        holder.itemView.isSelected = position == selectedPosition // Highlight the selected item
    }

    override fun getItemCount(): Int {
        return profileList.size
    }


    inner class ProfileViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.profileImageView)
        private val tvHeading: TextView = itemView.findViewById(R.id.profileTextView)

        fun bind(profile: Profile) {
            tvHeading.text = profile.firstName
        }
    }
}
