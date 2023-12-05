package com.myapp.firebase

import android.content.ContentValues
import android.util.Log

import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import com.myapp.firebase.FirebaseConnection.getDatabaseInstance

import kotlinx.coroutines.runBlocking
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
    suspend fun getAllDocumentsFromUsersCollection(collection: String): List<DocumentSnapshot> {
        return try {
            val collectionRef = database.collection(collection).get().await()
            collectionRef.documents
        } catch (e: Exception) {
            emptyList()
        }
    }

    fun printDocumentsFromCollection(collection: String) {
        val documents = runBlocking {
            getAllDocumentsFromUsersCollection(collection)
        }

        println("Documents in $collection:")
        for (document in documents) {
            println(document.data)
        }
    }
}
