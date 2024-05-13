package com.example.virtualdressup2


import android.view.View
import android.view.ViewGroup
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.*
import androidx.test.espresso.assertion.ViewAssertions.*
import androidx.test.espresso.matcher.ViewMatchers.*
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.hamcrest.Description
import org.hamcrest.Matcher
import org.hamcrest.Matchers
import org.hamcrest.Matchers.allOf
import org.hamcrest.TypeSafeMatcher
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@LargeTest
@RunWith(AndroidJUnit4::class)
class EndToEndUserFlow {

    @Rule
    @JvmField
    var mActivityScenarioRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun endToEndUserFlow() {
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

        val switch_ = onView(
            allOf(
                withId(R.id.darkModeSwitch), withText("Dark Mode"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.frame_layout),
                        6
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        switch_.perform(click())

        val bottomNavigationItemView2 = onView(
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
        bottomNavigationItemView2.perform(click())

        // Click on the spinner again to open the dropdown
        onView(withId(R.id.themeSpinner)).perform(click())

        // Click on the dropdown item at position 2
        Espresso.onData(Matchers.`is`("Green")).perform(click())

        val bottomNavigationItemView3 = onView(
            allOf(
                withId(R.id.home), withContentDescription("Home"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView3.perform(click())

        val bottomNavigationItemView4 = onView(
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
        bottomNavigationItemView4.perform(click())

        // Click on the spinner to open the dropdown
        onView(withId(R.id.themeSpinner)).perform(click())

        // Click on the dropdown item at position 1
        Espresso.onData(Matchers.`is`("Blue")).perform(click())

        val switch_2 = onView(
            allOf(
                withId(R.id.darkModeSwitch), withText("Dark Mode"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.frame_layout),
                        6
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        switch_2.perform(click())

        val bottomNavigationItemView5 = onView(
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
        bottomNavigationItemView5.perform(click())

        val bottomNavigationItemView6 = onView(
            allOf(
                withId(R.id.home), withContentDescription("Home"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView6.perform(click())

        val bottomNavigationItemView7 = onView(
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
        bottomNavigationItemView7.perform(click())

        val switch_3 = onView(
            allOf(
                withId(R.id.darkModeSwitch), withText("Dark Mode"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.frame_layout),
                        6
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        switch_3.perform(click())

        val bottomNavigationItemView8 = onView(
            allOf(
                withId(R.id.profile), withContentDescription("GalleryView"),
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
        bottomNavigationItemView8.perform(click())

        val bottomNavigationItemView9 = onView(
            allOf(
                withId(R.id.home), withContentDescription("Back"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView9.perform(click())

        val bottomNavigationItemView10 = onView(
            allOf(
                withId(R.id.profile), withContentDescription("NewOutfit"),
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
        bottomNavigationItemView10.perform(click())


        // Click on the spinner again to open the dropdown
        onView(withId(R.id.themeSpinner)).perform(click())

        // Click on the dropdown item at position 3
        Espresso.onData(Matchers.`is`("Purple")).perform(click())

        val bottomNavigationItemView11 = onView(
            allOf(
                withId(R.id.home), withContentDescription("Home"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.bottomNavigationView),
                        0
                    ),
                    0
                ),
                isDisplayed()
            )
        )
        bottomNavigationItemView11.perform(click())

        val bottomNavigationItemView12 = onView(
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
        bottomNavigationItemView12.perform(click())



        // Click on the spinner again to open the dropdown
        onView(withId(R.id.themeSpinner)).perform(click())

        // Click on the dropdown item at position 0
        Espresso.onData(Matchers.`is`("Default")).perform(click())

        val switch_4 = onView(
            allOf(
                withId(R.id.darkModeSwitch), withText("Dark Mode"),
                childAtPosition(
                    childAtPosition(
                        withId(R.id.frame_layout),
                        6
                    ),
                    4
                ),
                isDisplayed()
            )
        )
        switch_4.perform(click())
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
