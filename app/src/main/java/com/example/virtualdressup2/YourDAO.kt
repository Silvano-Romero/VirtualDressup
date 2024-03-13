package com.myapp.firebase

import android.content.ContentValues
import android.util.Log
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.tasks.await

class YourDAO : DAO() {
    private val database: FirebaseFirestore = FirebaseConnection().getDatabaseInstance()

    override suspend fun writeDocumentToCollection(collection: String, document: String, data: Any) {
        // Replace document content
        val documentReference = database.collection(collection).document(document)
        documentReference.set(data).await()

        // Create new document if it does not exist
        database.collection(collection).document(document)
            .set(data)
            .addOnSuccessListener { Log.d(ContentValues.TAG, "User Document Successfully Written!") }
            .addOnFailureListener { e -> Log.w(ContentValues.TAG, "Error Writing Document", e) }
    }

    override suspend fun getAllDocumentsFromCollection(collection: String): List<DocumentSnapshot> {
        return try {
            val collectionRef = database.collection(collection).get().await()
            collectionRef.documents
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun printDocumentsFromCollection(collection: String) {
        val documents = runBlocking {
            getAllDocumentsFromCollection(collection)
        }

        println("Documents in $collection:")
        for (document in documents) {
            println(document.data)
        }
    }
}
