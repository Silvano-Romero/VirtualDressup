// Package declaration specifying the package name for the class
package com.myapp.users

// Importing necessary classes and packages
import com.myapp.firebase.users.UserDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

// Declaration of the User class
class User constructor(
    private var firstName: String, // Property to store the first name of the user
    private var lastName: String, // Property to store the last name of the user
    private var userID: String // Property to store the user ID
) {
    // Initialization block that runs when an instance of the class is created
    init {
        print("User has been created!")
    }

    // Method to add user data to the database
    fun addUserToDatabase() {
        // Creating an instance of the UserDAO class
        val userDAO = UserDAO()
        // Creating a map to store user data
        val userDocumentData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )
        // Using coroutines to write user data to the database asynchronously
        GlobalScope.launch {
            userDAO.writeDocumentToCollection("Users", userID, userDocumentData)
        }
    }

    // Getter method to retrieve the user ID
    fun getUserID(): String { return this.userID }

    // Getter method to retrieve the first name of the user
    fun getFirstName(): String { return this.firstName }

    // Getter method to retrieve the last name of the user
    fun getLastName(): String { return this.lastName }

    // Setter method to update the first name of the user
    fun setFirstName(newFirstName: String) {
        this.firstName = newFirstName
    }

    // Setter method to update the last name of the user
    fun setLastName(newLastName: String) {
        this.lastName = newLastName
    }
}
