package com.myapp.users

import com.myapp.users.User
import org.junit.Test
class UserUnitTest {
    @Test
    fun validateUserAttributesTest(){
        val dummyUser1 = User(-1, "TestUserFirstName", "TestUserLastName")
        assert(dummyUser1.getUserID() == -1)
        assert(dummyUsdummyUser1er1.getFirstName() == "TestUserFirstName")
        assert(dummyUser1.getLastName() == "TestUserLastName")
    }
}