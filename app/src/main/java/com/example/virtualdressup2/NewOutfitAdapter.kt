package com.example.virtualdressup2

import android.annotation.SuppressLint
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Callback
import com.squareup.picasso.Picasso

class NewOutfitAdapter(
    private var modelList: List<RecyclerItem>,
) : RecyclerView.Adapter<NewOutfitAdapter.ModelViewHolder>() {

    // Create view holder for each item in the RecyclerView
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ModelViewHolder {
        val itemView = LayoutInflater.from(parent.context).inflate(R.layout.list_item, parent, false)
        return ModelViewHolder(itemView)
    }

    // Bind data to each item in the RecyclerView
    override fun onBindViewHolder(holder: ModelViewHolder, @SuppressLint("RecyclerView") position: Int) {
        val currentModel = modelList[position]
        holder.bindWithModelURL(currentModel)
    }

    override fun getItemCount(): Int {
        return modelList.size
    }

    // Inner class to define the view holder for each item
    inner class ModelViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val titleImage: ImageView = itemView.findViewById(R.id.outfitImage)
        private val tvHeading: TextView = itemView.findViewById(R.id.outfitName)

        fun bindWithModelURL(outfit: RecyclerItem) {
            // Set view to urlImage
            Picasso.get().load(outfit.modelID)
                .into(titleImage, object : Callback {
                    override fun onSuccess() {
                        // Image loaded successfully
                        tvHeading.text = outfit.heading
                    }

                    override fun onError(e: Exception?) {
                        // Log error and handle failure
                        Log.e("Model", "Error loading model image for outfit: ${outfit.heading}", e)
                    }
                })
        }
    }
}
