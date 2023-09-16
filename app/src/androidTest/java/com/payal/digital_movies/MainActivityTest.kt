package com.payal.digital_movies

import androidx.lifecycle.ViewModelProvider
import androidx.test.espresso.Espresso
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.payal.digital_movies.view.MainActivity
import com.payal.digital_movies.viewModel.MovieViewModel
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class MainActivityTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(MainActivity::class.java)

    @Test
    fun testSearchButton() {
        // Check if the search button is displayed
        Espresso.onView(ViewMatchers.withId(R.id.search))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

        // Perform a click action on the search button
        Espresso.onView(ViewMatchers.withId(R.id.search))
            .perform(ViewActions.click())

        // Check if the search view is visible after clicking the button
        Espresso.onView(ViewMatchers.withId(R.id.searchView))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun testSearchFunctionality() {
        // Simulate clicking the search button
        Espresso.onView(ViewMatchers.withId(R.id.search))
            .perform(ViewActions.click())

        // Type text into the search EditText
        Espresso.onView(ViewMatchers.withId(R.id.searchEditText))
            .perform(ViewActions.typeText("The"), ViewActions.closeSoftKeyboard())

        // Verify that the search results are displayed
        Espresso.onView(ViewMatchers.withId(R.id.recycler))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))

    }

    @Test
    fun testLoadingItemFromJsonIsSame() {
        var viewModel: MovieViewModel? = null
        activityRule.scenario.onActivity { activity ->
            viewModel = ViewModelProvider(activity).get(MovieViewModel::class.java)
        }
        lateinit var paginData:String
        runBlocking {
            paginData = viewModel?.loadJsonFromAssets(1)?.get(0)?.name?:""

        }

        Assert.assertEquals("The Birds", paginData)
    }

    @Test
    fun testSearchItemExistOrNot() {
        lateinit var viewModel: MovieViewModel
        activityRule.scenario.onActivity { activity ->
            viewModel = ViewModelProvider(activity).get(MovieViewModel::class.java)
        }

        // Now you can access the ViewModel and observe its LiveData
        val loadedData = viewModel.loadJsonFromAssets(1)
        val isRearExist = loadedData.filter { it.name.contains("Rear") }.isEmpty()
        val isBearExist = loadedData.filter { it.name.contains("Bear") }.isEmpty()

        Assert.assertEquals(false,isRearExist)
        Assert.assertEquals(true,isBearExist)

    }
}