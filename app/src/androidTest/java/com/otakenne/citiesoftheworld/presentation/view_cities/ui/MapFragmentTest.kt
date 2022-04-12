package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.MediumTest
import com.otakenne.citiesoftheworld.R
import com.otakenne.citiesoftheworld.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.mock
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class MapFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var navController: NavController

    @Before
    fun setup() {
        hiltRule.inject()

        navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MapFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun verifyBackButtonPopsBackStack() {
        pressBack()
        verify(navController).popBackStack()
    }

    @Test
    fun verifyMapShowsUp() {
        onView(withId(R.id.maps)).check(matches(isDisplayed()))
    }
}