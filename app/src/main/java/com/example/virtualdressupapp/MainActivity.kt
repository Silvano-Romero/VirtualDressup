package com.example.virtualdressupapp

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.activity.ComponentActivity
import com.myapp.firebase.UserDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch


class MainActivity : ComponentActivity() {
    private val userDAO = UserDAO
    private lateinit var editText: EditText
    private lateinit var submitButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        editText = findViewById(R.id.editText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val inputData = editText.text.toString()

            GlobalScope.launch {
                // Assuming your collection is named "Users"
                userDAO.writeDocumentToUsersCollection("Users", inputData, hashMapOf("Data:" to inputData))
            }
            // Clear the EditText after submitting
            editText.text.clear()
        }
        userDAO.printDocumentsFromCollection("Users")
    }
}

