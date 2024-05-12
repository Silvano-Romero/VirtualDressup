package com.example.virtualdressup2

import android.content.Intent
import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click

import androidx.test.espresso.intent.Intents
import androidx.test.espresso.intent.matcher.IntentMatchers.hasAction
import androidx.test.espresso.intent.matcher.IntentMatchers.hasExtraWithKey

import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.Matchers.allOf
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class GalleryActivityIntegrationTest {

    @Before
    fun setup() {
        // Initialize Intents for testing Intent-based interactions
        Intents.init()
    }

    @After
    fun tearDown() {
        // Release Intents after the test
        Intents.release()
    }

    @Test
    fun shareButton_ShouldStartShareIntent() {
        // Launch the GalleryActivity
        ActivityScenario.launch(GalleryActivity::class.java)

        // Click the share button
        onView(withId(R.id.shareButton)).perform(click())

        // Verify that the share intent is started
        Intents.intended(
            allOf(
                hasAction(Intent.ACTION_SEND),
                hasExtraWithKey(Intent.EXTRA_TEXT),
                hasExtraWithKey(Intent.EXTRA_STREAM),
                hasExtraWithKey(Intent.EXTRA_TITLE),
                hasExtraWithKey(Intent.EXTRA_INITIAL_INTENTS)
            )
        )
    }
}