package com.myapp.firebase.revery

import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.myapp.firebase.Avatar
import com.myapp.firebase.DAO
import com.myapp.firebase.Outfit
import kotlinx.coroutines.tasks.await

private var profilesCollectionName = "Profiles"
class AvatarDAO : DAO(){
    suspend fun getSpecificAvatar(avatarID: String): Avatar? {
        var profiles: List<DocumentSnapshot> = getAllDocumentsFromCollection(profilesCollectionName)
        var outfitsList: MutableList<Outfit> = mutableListOf()
        for(profile in profiles){
            if(profile.id == avatarID) {
                println("Document ID: ${profile.id}")
                println("Document Model: ${profile.get("ModelID")}")
                println("Document Outfits: ${profile.reference.collection("Outfits")}")

                var outfits: CollectionReference = profile.reference.collection("Outfits")
                outfits.get()
                    .addOnSuccessListener { querySnapshot: QuerySnapshot ->
                        // Handle query snapshot
                        if (!querySnapshot.isEmpty) {

                            for (outfit in querySnapshot.documents) {

                                // Get fields from each outfit
                                val outfitID = outfit.id
                                val bottomGarmentID: String  = outfit.getString("Bottom") as String
                                val topGarmentID: String = outfit.getString("Top") as String

                                // Create custom objects or perform other actions
                                println("Outfit ID: $outfitID, Bottom: $bottomGarmentID, Top: $topGarmentID")
                                outfitsList.add(Outfit(outfitID, topGarmentID, bottomGarmentID))
                                println(outfitsList)

                            }
                        } else {
                            println("No documents found in the outfits.")
                        }
                    }
                    .addOnFailureListener { exception ->
                        println("Error fetching documents: ${exception.message}")
                    }.await()
                return Avatar(avatarID, outfitsList)
            }
        }
        return null
    }
}