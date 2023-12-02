package com.myapp.firebase

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.FirebaseConnection.getDatabaseInstance
import kotlinx.coroutines.tasks.await

/*
    User Data Access Object to write and delete from Users collection.
 */
object UserDAO {
    private var database: FirebaseFirestore = getDatabaseInstance()

    suspend fun writeDocumentToUsersCollection(collection: String, document: String, data: Any) {
        // Replace document content
        val documentReference = database.collection(collection).document(document)
        documentReference.set(data).await()

        // Create new document if it does not exist
        database.collection(collection).document(document)
                .set(data)
                .addOnSuccessListener { Log.d(ContentValues.TAG, "User Document Successfully Written!") }
                .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error Writing Document", e) }
    }
}
