package com.myapp.firebase
import java.util.UUID

data class Avatar(
    var avatarID: String,
    val modelID: String,
    val outfits: List<Outfit>,
) {

    constructor(modelID: String, outfits: List<Outfit>) : this("", modelID, outfits) {
        this.avatarID = UUID.randomUUID().toString().take(13)
    }
    constructor() : this("", "", mutableListOf())
}


data class Outfit(
    var outfitID: String,
    val top: String,
    val bottom: String,
    val modelFile: String?,
){
    constructor(top: String, bottom: String, modelFile: String) : this("", top, bottom, modelFile) {
        this.outfitID = UUID.randomUUID().toString().take(13)
    }
    constructor() : this("", "","", "")
}

data class Calendar(
    val outfitImageUrl: String,
){constructor() : this("")}
