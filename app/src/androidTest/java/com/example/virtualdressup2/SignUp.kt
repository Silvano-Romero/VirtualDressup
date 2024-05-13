package com.example.virtualdressup2


import androidx.test.espresso.DataInteraction
import androidx.test.espresso.ViewInteraction
import androidx.test.filters.LargeTest
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import android.view.View
import android.view.ViewGroup
import android.view.ViewParent

import androidx.test.InstrumentationRegistry.getInstrumentation
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.Espresso.openActionBarOverflowOrOptionsMenu
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*

import com.example.virtualdressup2.R

import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.TypeSafeMatcher
import org.hamcrest.core.IsInstanceOf
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.`is`

@LargeTest
@RunWith(AndroidJUnit4::class)
class SignUp {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(SplashScreen::class.java)

    @Test
    fun signUp() {
        val materialTextView = onView(
        allOf(withId(R.id.sign_up_button), withText("Sign Up"),
        childAtPosition(
        childAtPosition(
        withId(android.R.id.content),
        0),
        6),
        isDisplayed()))
                materialTextView.perform(click())

                val textInputEditText = onView(
        allOf(withId(R.id.firstNameEt),
        childAtPosition(
        childAtPosition(
        withId(R.id.firstNameLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText.perform(replaceText("Test"), closeSoftKeyboard())

                val textInputEditText2 = onView(
        allOf(withId(R.id.firstNameEt), withText("Test"),
        childAtPosition(
        childAtPosition(
        withId(R.id.firstNameLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText2.perform(click())

                val textInputEditText3 = onView(
        allOf(withId(R.id.firstNameEt), withText("Test"),
        childAtPosition(
        childAtPosition(
        withId(R.id.firstNameLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText3.perform(click())

                val textInputEditText4 = onView(
        allOf(withId(R.id.firstNameEt), withText("Test"),
        childAtPosition(
        childAtPosition(
        withId(R.id.firstNameLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText4.perform(replaceText("Unit"))

                val textInputEditText5 = onView(
        allOf(withId(R.id.firstNameEt), withText("Unit"),
            childAtPosition(
            childAtPosition(
            withId(R.id.firstNameLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText5.perform(closeSoftKeyboard())

                val textInputEditText6 = onView(allOf(withId(R.id.lastNameEt),
            childAtPosition(
            childAtPosition(
            withId(R.id.lastNameLayout),
        0),
        0),
            isDisplayed()))
                textInputEditText6.perform(replaceText("Test"), closeSoftKeyboard())

                val textInputEditText7 = onView(
        allOf(withId(R.id.emailEt),
            childAtPosition(
            childAtPosition(
            withId(R.id.emailLayout),
        0),
        0),
            isDisplayed()))
                textInputEditText7.perform(replaceText("unit@y.mail"), closeSoftKeyboard())

                val textInputEditText8 = onView(
        allOf(withId(R.id.emailEt), withText("unit@y.mail"),
            childAtPosition(
            childAtPosition(
            withId(R.id.emailLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText8.perform(pressImeActionButton())

                val textInputEditText9 = onView(
        allOf(withId(R.id.passET),
            childAtPosition(
            childAtPosition(
            withId(R.id.passwordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText9.perform(replaceText("test"), closeSoftKeyboard())

                val textInputEditText10 = onView(
        allOf(withId(R.id.passET), withText("test"),
            childAtPosition(
             childAtPosition(
            withId(R.id.passwordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText10.perform(pressImeActionButton())

                val textInputEditText11 = onView(
        allOf(withId(R.id.confirmPassEt),
        childAtPosition(
        childAtPosition(
        withId(R.id.confirmPasswordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText11.perform(replaceText("test"), closeSoftKeyboard())

                val textInputEditText12 = onView(
        allOf(withId(R.id.confirmPassEt), withText("test"),
        childAtPosition(
        childAtPosition(
        withId(R.id.confirmPasswordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText12.perform(pressImeActionButton())

                val appCompatButton = onView(
        allOf(withId(R.id.sign_up_button), withText("Sign Up"),
        childAtPosition(
        childAtPosition(
        withId(android.R.id.content),
        0),
        7),
        isDisplayed()))
                appCompatButton.perform(click())

                val appCompatButton2 = onView(
        allOf(withId(R.id.sign_up_button), withText("Sign Up"),
        childAtPosition(
        childAtPosition(
        withId(android.R.id.content),
        0),
        7),
        isDisplayed()))
                appCompatButton2.perform(click())

                val textInputEditText13 = onView(
        allOf(withId(R.id.passET), withText("test"),
        childAtPosition(
        childAtPosition(
        withId(R.id.passwordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText13.perform(replaceText("test12"))

                val textInputEditText14 = onView(
        allOf(withId(R.id.passET), withText("test12"),
        childAtPosition(
        childAtPosition(
        withId(R.id.passwordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText14.perform(closeSoftKeyboard())

                val textInputEditText15 = onView(
        allOf(withId(R.id.passET), withText("test12"),
        childAtPosition(
        childAtPosition(
        withId(R.id.passwordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText15.perform(pressImeActionButton())

                val textInputEditText16 = onView(
        allOf(withId(R.id.confirmPassEt), withText("test"),
        childAtPosition(
        childAtPosition(
        withId(R.id.confirmPasswordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText16.perform(replaceText("test12"))

                val textInputEditText17 = onView(
        allOf(withId(R.id.confirmPassEt), withText("test12"),
        childAtPosition(
        childAtPosition(
        withId(R.id.confirmPasswordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText17.perform(closeSoftKeyboard())

                val textInputEditText18 = onView(
        allOf(withId(R.id.confirmPassEt), withText("test12"),
        childAtPosition(
        childAtPosition(
        withId(R.id.confirmPasswordLayout),
        0),
        0),
        isDisplayed()))
                textInputEditText18.perform(pressImeActionButton())

                val appCompatButton3 = onView(
        allOf(withId(R.id.sign_up_button), withText("Sign Up"),
        childAtPosition(
        childAtPosition(
        withId(android.R.id.content),
        0),
        7),
        isDisplayed()))
        appCompatButton3.perform(click())
        }

    private fun childAtPosition(
            parentMatcher: Matcher<View>, position: Int): Matcher<View> {

        return object : TypeSafeMatcher<View>() {
            override fun describeTo(description: Description) {
                description.appendText("Child at position $position in parent ")
                parentMatcher.describeTo(description)
            }

            public override fun matchesSafely(view: View): Boolean {
                val parent = view.parent
                return parent is ViewGroup && parentMatcher.matches(parent)
                        && view == parent.getChildAt(position)
            }
        }
    }
    }
