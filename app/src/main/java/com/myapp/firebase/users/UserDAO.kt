package com.myapp.firebase.users

import android.content.ContentValues
import android.util.Log

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.DAO
import com.myapp.firebase.FirebaseConnection

import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

/*
    User Data Access Object to write and delete from Users collection.
 */
class UserDAO : DAO() {
    private var database: FirebaseFirestore = FirebaseConnection().getDatabaseInstance()

    suspend fun getUserFromUsersCollection(collection: String, userID: Int): Map<String, Any>? {
        val userDocument = database.collection(collection).document(userID.toString()).get().await()
        return if (userDocument.exists()) {
            userDocument.data
        } else {
            null
        }
    }
}
