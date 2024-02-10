package com.example.virtualdressup2

import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import com.myapp.firebase.users.UserDAO

class InputData: AppCompatActivity() {
    private val userDAO = UserDAO()
    private lateinit var editText: EditText
    private lateinit var submitButton: Button
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.input_data)

        editText = findViewById(R.id.editText)
        submitButton = findViewById(R.id.submitButton)

        submitButton.setOnClickListener {
            val inputData = editText.text.toString()

            GlobalScope.launch {
                // Assuming your collection is named "Users"
                userDAO.writeDocumentToCollection("Users", inputData, hashMapOf("Data:" to inputData))
            }
            // Clear the EditText after submitting
            editText.text.clear()
        }
        userDAO.printDocumentsFromCollection("Users")
    }
}