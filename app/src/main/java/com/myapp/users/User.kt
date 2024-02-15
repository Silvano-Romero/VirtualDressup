package com.myapp.users

import com.myapp.firebase.users.UserDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import java.util.UUID

class User constructor(private var firstName: String, private var lastName: String) {
    private val userID: String = generateShortUUID()

    init {
        print("User has been created!")
    }

    fun addUserToDatabase() {
        val userDAO = UserDAO()
        val userDocumentData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )
        GlobalScope.launch {
            userDAO.writeDocumentToCollection("Users", userID, userDocumentData)
        }
    }

    fun getUserID(): String { return this.userID }
    fun getFirstName(): String { return this.firstName }
    fun getLastName(): String { return this.lastName }
    fun setFirstName(newFirstName: String) {
        this.firstName = newFirstName
    }
    fun setLastName(newLastName: String) {
        this.lastName = newLastName
    }

    private fun generateShortUUID(): String {
        val uuid = UUID.randomUUID().toString()
        return uuid.substring(0, 8) // Take the first 8 characters of the UUID string
    }
}
