package com.myapp.users

import java.io.Serializable

data class Profile(
    var firstName: String = "",
) : Serializable {
    init {
        println("Profile has been created!")
    }
}



