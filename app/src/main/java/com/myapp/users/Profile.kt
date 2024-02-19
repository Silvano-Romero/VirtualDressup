package com.myapp.users

import com.myapp.firebase.users.ProfileDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Profile(
    private var firstName: String,
) {
    init {
        println("Profile has been created!")
    }


    fun getFirstName(): String { return firstName }


    fun setFirstName(newFirstName: String) { firstName = newFirstName }


    fun saveProfileToDatabase() {
        val profileDAO = ProfileDAO()
        val profileDocumentData = hashMapOf(

            "firstName" to firstName,
        )
        GlobalScope.launch {
            profileDAO.writeDocumentToCollection("Profiles", firstName, profileDocumentData)
        }
    }
}