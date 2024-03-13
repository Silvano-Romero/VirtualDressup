package com.example.virtualdressup2

// Data class representing an item in a RecyclerView
data class RecyclerItem(
    var titleImage: Int, // Resource ID of the image associated with the item
    var heading: String, // Text heading of the item
    val garmentID: String? = null,
    val gender: String? = null,
    val category: String? = null,
    val modelID: String? = null,
)
