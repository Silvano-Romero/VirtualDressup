package com.myapp.users

import org.junit.Test

class accountUnitTest {
    @Test
    fun validateAccountAttributesTest(){
        val dummyUser1 = User(-1, "TestUserFirstName", "TestUserLastName")
        val dummyAccount1 = Account(dummyUser1, "dummyUser@email.com", "dummyPassword")
        assert(dummyAccount1.getAccountNumber() == -1)
        assert(dummyAccount1.getEmail() == "dummyUser@email.com")
        assert(dummyAccount1.getPassword() == "dummyPassword")
    }
}