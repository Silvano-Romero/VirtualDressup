package com.example.virtualdressup2

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.myapp.firebase.Avatar

class AvatarAdapter(
    private val avatarList: MutableList<Avatar>, // List of avatars to display
    private val onItemClick: (Avatar, position: Int) -> Unit // Callback function for item click events
) : RecyclerView.Adapter<AvatarAdapter.AvatarViewHolder>() {

    var selectedPosition = RecyclerView.NO_POSITION

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AvatarViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_profile, parent, false)
        return AvatarViewHolder(view)
    }

    override fun onBindViewHolder(holder: AvatarAdapter.AvatarViewHolder, position: Int) {
        val avatar = avatarList[position]
        holder.bind(avatar)

        // Set click listener for the item
        holder.itemView.setOnClickListener {
            // Update the selected position and invoke onItemClick callback
            selectedPosition = position
            onItemClick(avatar, position)
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
        return avatarList.size
    }


    inner class AvatarViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.profileImageView)
        private val tvHeading: TextView = itemView.findViewById(R.id.profileTextView)


        fun bind(avatar: Avatar) {
            tvHeading.text = avatar.firstName
        }
    }
}
