package com.myapp.users

import com.myapp.firebase.users.UserDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class User constructor(
    private var firstName: String,
    private var lastName: String,
    private var userID: String // Parameter to store the user ID
) {
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
}
