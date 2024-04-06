package com.myapp.firebase.users

import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.DAO
import com.myapp.firebase.FirebaseConnection
import kotlinx.coroutines.tasks.await


class ProfileDAO : DAO() {
    private var database: FirebaseFirestore = FirebaseConnection().getDatabaseInstance()

    /**
     * Retrieves profile data from the Profiles collection.
     * @param userID The ID of the user.
     * @return A map representing the profile data, or null if the profile doesn't exist.
     */
    suspend fun getProfileFromProfilesCollection(userID: Int): Map<String, Any>? {
        val profileDocument = database.collection("Profiles").document(userID.toString()).get().await()
        return if (profileDocument.exists()) {
            profileDocument.data
        } else {
            null
        }
    }

    /**
     * Writes profile data to the Profiles collection.
     * @param userID The ID of the user.
     * @param profileData The profile data to write.
     */
    suspend fun writeProfileToProfilesCollection(userID: Int, profileData: Map<String, Any>) {
        writeDocumentToCollection("Profiles", userID.toString(), profileData)
        val profileDocRef = database.collection("Profiles").document(userID.toString())
        println("PROFILE_REF $profileDocRef")

        val subCol = profileDocRef.collection("Avatars")
            .document("DefaultAvatar")
            .set(mapOf(
            "Model" to "DefaultModelID"
            ))
    }
}