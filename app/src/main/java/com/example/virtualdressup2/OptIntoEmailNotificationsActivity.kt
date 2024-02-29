package com.example.virtualdressup2

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class OptIntoEmailNotificationsActivity : AppCompatActivity() {
    private lateinit var optIntoNotificationsButton: Button
    private lateinit var emailEditText: EditText
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.opt_into_email_notifications)

        optIntoNotificationsButton = findViewById(R.id.opt_into_notifications_button)
        emailEditText = findViewById(R.id.emailEt)
        optIntoNotificationsButton.setOnClickListener {
            val emailAddress = emailEditText.text.toString().trim()

            if (emailAddress.isNotEmpty()) {
                sendEmail(emailAddress)
            } else {
                Toast.makeText(this, "Please enter an email address", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun sendEmail(emailAddress: String) {
        val subject = "Subject of your email"
        val message = "Message body of your email"

        val intent = Intent(Intent.ACTION_SEND)
        intent.type = "message/rfc822"
        intent.putExtra(Intent.EXTRA_EMAIL, arrayOf(emailAddress))
        intent.putExtra(Intent.EXTRA_SUBJECT, subject)
        intent.putExtra(Intent.EXTRA_TEXT, message)

        try {
            startActivity(Intent.createChooser(intent, "Choose an email client"))
        } catch (e: Exception) {
            Toast.makeText(this, "Failed to open email client", Toast.LENGTH_SHORT).show()
        }
    }
}
