package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
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
import org.mockito.Mockito
import org.mockito.Mockito.verify

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
class ListFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    private lateinit var navController: NavController

    @Before
    fun setup() {
        hiltRule.inject()

        navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }
    }

    @Test
    fun verifySwitchToMapViewButtonNavigatesToMapFragment() {
        onView(withId(R.id.switch_to_map_view)).perform(click())
        verify(navController).navigate(
            ListFragmentDirections.actionListFragmentToMapFragment()
        )
    }

    @Test
    fun verifySwitchToMapViewButtonShowsUp() {
        onView(withId(R.id.switch_to_map_view)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun verifySearchFieldShowsUp() {
        onView(withId(R.id.search)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun verifySearchLayoutShowsUp() {
        onView(withId(R.id.search_layout)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    @Test
    fun verifyCityListRecyclerViewShowsUp() {
        onView(withId(R.id.city_list)).check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }
}