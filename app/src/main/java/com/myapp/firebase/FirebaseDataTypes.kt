package com.myapp.firebase
import com.google.firebase.firestore.PropertyName
import java.io.Serializable
import java.util.UUID

data class Avatar(
    var avatarID: String,
    val modelID: String,
    @get:PropertyName("FirstName") @set:PropertyName("FirstName") var firstName: String,
    val gender: String,
    val outfits: List<Outfit>,
) : Serializable {
    constructor(modelID: String, outfits: List<Outfit>) : this("", "","","", outfits) {
        this.avatarID = UUID.randomUUID().toString().take(13)
    }
    constructor() : this("", "","","", mutableListOf())
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

