package com.myapp.firebase.revery

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.Avatar
import com.myapp.firebase.DAO
import com.myapp.firebase.FirebaseConnection
import com.myapp.firebase.Outfit
import kotlinx.coroutines.tasks.await

private var profilesCollectionName = "Profiles"
private var avatarsCollectionName = "Avatars"
private var outfitsSubCollectionName = "Outfits"
private var calendarCollectionName = "calendar"
class AvatarDAO : DAO() {
    private var database: FirebaseFirestore = FirebaseConnection().getDatabaseInstance()
    // This method retrieves all avatars from a specific profile and returns a list of
    // avatar data types.
    suspend fun getAvatarsFromProfile(profileID: String): List<Avatar> {
        var profiles: List<DocumentSnapshot> = getAllDocumentsFromCollection(profilesCollectionName)
        var avatarsList: MutableList<Avatar> = mutableListOf()

        for (profile in profiles) {

            if (profile.id == profileID) {
                var avatars: CollectionReference = profile.reference.collection(avatarsCollectionName)
                var avatarID: String = ""
                // Await avatars.get()
                val avatarsSnapshot = avatars.get().await()
                if (!avatarsSnapshot.isEmpty) {
                    // Iterate through all avatars and create avatar object
                    for (avatar in avatarsSnapshot.documents) {
                        println("OUTFIT: $avatar")
                        var outfitsList: MutableList<Outfit> = mutableListOf()
                        avatarID = avatar.id
                        val outfitsRef = avatar.reference.collection(outfitsSubCollectionName)
                        val outfitsSnapshot = outfitsRef.get().await()
                        val modelID: String  = avatar.getString("Model") as String
                        // Gather all the outfits for each avatar and create outfit object
                        if (!outfitsSnapshot.isEmpty) {
                            for (outfit in outfitsSnapshot.documents) {
                                println("OUTFIT: $outfit")
                                val outfitID = outfit.id
                                val bottomGarmentID: String  = outfit.getString("Bottom") as String
                                val topGarmentID: String = outfit.getString("Top") as String
                                val modelFile: String = outfit.getString("ModelFile") as String
                                println("ModelFILE = $modelFile")
                                outfitsList.add(Outfit(outfitID, topGarmentID, bottomGarmentID, modelFile))
                            }
                        } else {
                            println("No documents found in the outfits.")
                        }
                        avatarsList.add(Avatar(avatarID, modelID, outfitsList))
                    }
                } else {
                    println("No documents found in the avatars.")
                }
            }
        }

        return avatarsList
    }
    // Get a specific avatar from a profile
    suspend fun getSpecificAvatarFromProfile(profileID: String, avatarID: String): Avatar{
        val avatars = getAvatarsFromProfile(profileID)
        for(avatar in avatars){
            if(avatar.avatarID == avatarID){
                return avatar
            }
        }
        return Avatar()
    }

    suspend fun deleteOutfitFromAvatar(profileID: String, avatarID: String, outfitIdToDelete: String){
        var outfitRef = database.collection(profilesCollectionName)
            .document(profileID)
            .collection(avatarsCollectionName)
            .document(avatarID)
            .collection(outfitsSubCollectionName)
            .document(outfitIdToDelete)

        println(outfitRef)
        outfitRef.delete()
            .addOnSuccessListener {
                // Document successfully deleted
                println("DocumentSnapshot successfully deleted!")
            }
            .addOnFailureListener { e ->
                // Handle any errors
                println("Error deleting document: $e")
            }
    }


    // Get all available profiles
    suspend fun getAllProfileIDs(): List<String>{
        var profiles: List<DocumentSnapshot> = getAllDocumentsFromCollection(profilesCollectionName)
        var profileIDS: MutableList<String> = mutableListOf()
        for(profile in profiles){
            profileIDS.add(profile.id)
        }

        return profileIDS
    }

    // Add an avatar document to a profile. Avatar will be added with a list of outfits and model id.
    suspend fun addAvatarToProfile(profileID: String, avatar: Avatar){
        var avatarsCollRef = database.collection(profilesCollectionName)
            .document(profileID.toString())
            .collection(avatarsCollectionName)

        var avatarDocRef = avatarsCollRef.document(avatar.avatarID)
        avatarDocRef.set(mapOf(
            "Model" to avatar.modelID,
        ))

        var outfitsSubCollRef = avatarDocRef.collection(outfitsSubCollectionName)
        for(outfit in avatar.outfits){
            var outfitDocRef = outfitsSubCollRef.document(outfit.outfitID)
                .set(mapOf(
                    "Top" to outfit.top,
                    "Bottom" to outfit.bottom,
                    "ModelFile" to outfit.modelFile
                    ))
        }
    }
    // Add an outfit dociment to outfits collection of a specific avatar document.
    suspend fun addOutfitToAvatar(profileID: String, avatarID: String, outfit: Outfit){
        var outfitsCollRef = database.collection(profilesCollectionName)
            .document(profileID)
            .collection(avatarsCollectionName)
            .document(avatarID)
            .collection(outfitsSubCollectionName)
        outfitsCollRef.document(outfit.outfitID)
            .set(mapOf(
                "Top" to outfit.top,
                "Bottom" to outfit.bottom,
                "ModelFile" to outfit.modelFile
            ))
    }
}

