package com.payal.evaluateexpressions

import androidx.test.core.app.ActivityScenario
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.LargeTest
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
@LargeTest
class MainActivityTest {
    @Before
    fun setUp() {
        // Launch the MainActivity
        val scenario = ActivityScenario.launch(MainActivity::class.java)
    }

    @Test
    fun testFragmentIsDisplayed() {
        // Verify that the EvaluateExpressionFragment is displayed
        Espresso.onView(ViewMatchers.withId(R.id.frameLayout))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}