package com.example.virtualdressup2
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import com.myapp.users.Account
import com.myapp.firebase.users.AccountDAO
import com.myapp.users.User

class ProfileCreationActivity : AppCompatActivity() {

    private lateinit var emailEditText: EditText
    private lateinit var user: User
    private lateinit var account: Account

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.profile_creation)

        emailEditText = findViewById(R.id.email_edittext)

        // Get the user object from the intent or any other source
        user = intent.getSerializableExtra("user") as User
    }

    fun createProfile(view: View) {
        val email = emailEditText.text.toString()

        // Get the user ID from the user object
        val userID = user.getUserID()
        val password = account.getPassword()

        // Create an Account object and save it to the database
        val account = Account(user, email, password) // Pass the user object
        account.addAccountToDatabase()

        // You may also add additional logic here, such as navigating to another activity
    }
}