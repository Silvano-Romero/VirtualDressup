package com.myapp.users

import com.myapp.firebase.users.ProfileDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Profile(
    private var userID: Int,
    private var firstName: String,
    private var lastName: String,
    private var email: String,
    private var phoneNumber: String,
    private var address: String
) {
    init {
        println("Profile has been created!")
    }

    fun getUserID(): Int { return userID }
    fun getFirstName(): String { return firstName }
    fun getLastName(): String { return lastName }
    fun getEmail(): String { return email }
    fun getPhoneNumber(): String { return phoneNumber }
    fun getAddress(): String { return address }

    fun setFirstName(newFirstName: String) { firstName = newFirstName }
    fun setLastName(newLastName: String) { lastName = newLastName }
    fun setEmail(newEmail: String) { email = newEmail }
    fun setPhoneNumber(newPhoneNumber: String) { phoneNumber = newPhoneNumber }
    fun setAddress(newAddress: String) { address = newAddress }

    fun saveProfileToDatabase(accountNumber: Int) {
        val profileDAO = ProfileDAO()
        val profileDocumentData = hashMapOf(
            "userID" to userID,
            "firstName" to firstName,
            "lastName" to lastName,
            "email" to email,
            "phoneNumber" to phoneNumber,
            "address" to address,
            "accountNumber" to accountNumber
        )
        GlobalScope.launch {
            profileDAO.writeDocumentToCollection("Profiles", userID.toString(), profileDocumentData)
        }
    }
}