package com.myapp.firebase

data class Avatar(
    val modelID: String,
    val outfits: List<Outfit>,
)

data class Outfit(
    val outfitID: String,
    val top: String,
    val bottom: String,
)
