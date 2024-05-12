package com.myapp.users

import com.myapp.users.User
import org.junit.Test
class UserUnitTest {
    @Test
    fun validateUserAttributesTest(){
        val dummyUser1 = User("TestUserFirstName", "TestUserLastName", "123")
        assert(dummyUser1.getFirstName() == "TestUserFirstName")
        assert(dummyUser1.getLastName() == "TestUserLastName")
    }
}