package com.myapp.firebase.users

import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.DAO
import com.myapp.firebase.FirebaseConnection
import kotlinx.coroutines.tasks.await

/*
    Profile Data Access Object to write and delete from Profiles collection.
 */
class ProfileDAO : DAO() {
    private var database: FirebaseFirestore = FirebaseConnection().getDatabaseInstance()

    suspend fun getProfileFromProfilesCollection(userID: Int): Map<String, Any>? {
        val profileDocument = database.collection("Profiles").document(userID.toString()).get().await()
        return if (profileDocument.exists()) {
            profileDocument.data
        } else {
            null
        }
    }

    suspend fun writeProfileToProfilesCollection(userID: Int, profileData: Map<String, Any>) {
        writeDocumentToCollection("Profiles", userID.toString(), profileData)
    }
}