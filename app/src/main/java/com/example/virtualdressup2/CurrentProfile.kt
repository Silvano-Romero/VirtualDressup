package com.example.virtualdressup2

object CurrentProfile {
    var profileID: String = ""
    var gender: String = ""
    fun details() : String{
        var details = ":DETAILS: PROFILE_ID: $profileID, GENDER: $gender"
        return details

    }

}