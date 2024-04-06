package com.myapp.firebase

data class Avatar(
    val avatarID: String,
    val modelID: String,
    val outfits: List<Outfit>,
){constructor() : this("", "", mutableListOf())}

data class Outfit(
    val outfitID: String,
    val top: String,
    val bottom: String,
){constructor() : this("", "","")}
