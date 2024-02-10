package com.myapp.users
import com.myapp.firebase.users.AccountDAO
import com.myapp.firebase.users.UserDAO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class Account constructor(private var user: User, private var email: String, private var password: String){
    private var accountNumber = user.getUserID()
    init{
        print("Account has been created!")
    }

    fun addAccountToDatabase(){
        val accountDAO = AccountDAO()
        val accountDocumentData = hashMapOf(
            "userID" to accountNumber,
            "email"  to email,
            "password" to password
        )
        GlobalScope.launch {
            accountDAO.writeDocumentToCollection("Accounts", accountNumber.toString(), accountDocumentData)
        }
    }

    fun getAccountNumber(): Int { return this.accountNumber }
    fun getUserID(): Int { return this.user.getUserID() }
    fun getEmail(): String { return this.email }
    fun getPassword(): String { return this.password }
    fun setNewPassword(newPassword: String){
        this.password = newPassword
    }
}