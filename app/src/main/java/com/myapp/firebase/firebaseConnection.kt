package com.myapp.firebase

import com.google.firebase.Firebase
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore

object FirebaseConnection {
    private val database: FirebaseFirestore = Firebase.firestore

    fun getDatabaseInstance(): FirebaseFirestore {
        return database
    }
}
