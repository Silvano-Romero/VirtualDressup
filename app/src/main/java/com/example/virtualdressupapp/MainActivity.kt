package com.example.virtualdressupapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

import com.myapp.firebase.UserDAO.writeDocumentToUsersCollection

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val userErwin = hashMapOf(
            "name" to "Erwin",
            "age" to 20,
        )

        val userSilvano = hashMapOf(
            "name" to "Silvano",
            "age" to 21,
        )

        val userGabriel = hashMapOf(
            "name" to "Gabriel",
            "age" to 22,
        )

        val collection = "Users"

        GlobalScope.launch {
            writeDocumentToUsersCollection(collection, "Erwin", userErwin)
            writeDocumentToUsersCollection(collection, "Silvano", userSilvano)
            writeDocumentToUsersCollection(collection, "Gabriel", userGabriel)
        }
    }
}
