package com.example.virtualdressup2

import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso.onData
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.RootMatchers
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers.allOf
import org.hamcrest.Matchers.anything
import org.hamcrest.Matchers.instanceOf
import org.hamcrest.Matchers.`is`
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class UICustomization {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun uICustomization() {
        val bottomNavigationItemView = onView(
            allOf(
                withId(R.id.profile), withContentDescription("Settings"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    1
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView.perform(click())

        // Click on the spinner to open the dropdown
        onView(withId(R.id.themeSpinner)).perform(click())

        // Click on the dropdown item at position 1
        onData(`is`("Blue")).perform(click())

        // Click on the spinner again to open the dropdown
        onView(withId(R.id.themeSpinner)).perform(click())

        // Click on the dropdown item at position 2
        onData(`is`("Green")).perform(click())

        // Click on the spinner again to open the dropdown
        onView(withId(R.id.themeSpinner)).perform(click())

        // Click on the dropdown item at position 3
        onData(`is`("Purple")).perform(click())

        // Click on the spinner again to open the dropdown
        onView(withId(R.id.themeSpinner)).perform(click())

        // Click on the dropdown item at position 0
        onData(`is`("Default")).perform(click())
    }

    private fun childAtPosition(
        parentMatcher: Matcher<View>, position: Int
    ): Matcher<View> {

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