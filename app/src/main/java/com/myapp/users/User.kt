package com.myapp.users
import com.myapp.firebase.UserDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class User constructor(private var userID: Int, private var firstName: String, private var lastName: String){
    init {
        print("User has been created!")
    }

    fun addUserToDatabase(){
        val userDAO = UserDAO()
        val userDocumentData = hashMapOf(
            "firstName" to firstName,
            "lastName" to lastName
        )
        GlobalScope.launch {
            userDAO.writeDocumentToUsersCollection("Users", userID.toString(), userDocumentData)
        }
    }

    fun getUserID(): Int { return this.userID }
    fun getFirstName(): String { return this.firstName }
    fun getLastName(): String { return this.lastName }
    fun setFirstName(newFirstName: String){
        this.firstName = newFirstName
    }
    fun setLastName(newLastName: String){
        this.lastName = newLastName
    }
}