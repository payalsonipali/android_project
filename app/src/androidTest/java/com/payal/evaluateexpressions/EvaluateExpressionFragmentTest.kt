package com.payal.evaluateexpressions

import androidx.fragment.app.testing.FragmentScenario
import androidx.fragment.app.testing.launchFragmentInContainer
import androidx.lifecycle.Lifecycle
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.action.ViewActions.typeText
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.espresso.matcher.ViewMatchers.withText
import androidx.test.ext.junit.runners.AndroidJUnit4

import org.junit.Test
import org.junit.runner.RunWith

import org.junit.Assert.*
import org.junit.Before

@RunWith(AndroidJUnit4::class)
class EvaluateExpressionFragmentTest {

    private lateinit var scenario:FragmentScenario<EvaluateExpressionFragment>

    @Before
    fun setUp() {
        // Launch the MainActivity
       // val scenario = ActivityScenario.launch(MainActivity::class.java)
        scenario = launchFragmentInContainer(themeResId = R.id.fragment_evaluate_expression)
        scenario.moveToState(Lifecycle.State.STARTED)
    }
    

    @Test
    fun testEvaluateWorkingFine() {
        val expression = "2+2" // Expression to enter in the EditText

        // Type the expression into the EditText
        onView(withId(R.id.expressionsEditText)).perform(typeText(expression))

        // Close the soft keyboard (if it's open)
        Espresso.closeSoftKeyboard()

        // Click the evaluate button
        onView(withId(R.id.evaluateButton)).perform(click())

        // Verify that the resultsTextView contains the expected text
        onView(withId(R.id.resultsTextView)).check(matches(withText("2+2 >> 4")))
    }

    @Test
    fun testWhenEditTextIsEmpty() {
        val expression = "" // Expression to enter in the EditText

        // Type the expression into the EditText
        onView(withId(R.id.expressionsEditText)).perform(typeText(expression))

        // Close the soft keyboard (if it's open)
        Espresso.closeSoftKeyboard()

        // Click the evaluate button
        onView(withId(R.id.evaluateButton)).perform(click())

        // Verify that the resultsTextView contains the expected text
        onView(withText("• Please type something to evaluate")).check(matches(isDisplayed()))
    }
}