package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.action.ViewActions.click
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
//@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class ListFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun frag() {
        val navController = Mockito.mock(NavController::class.java)
        launchFragmentInHiltContainer<ListFragment> {
            Navigation.setViewNavController(requireView(), navController)
        }

        onView(withId(R.id.switch_to_map_view)).perform(click())
        verify(navController).navigate(
            ListFragmentDirections.actionListFragmentToMapFragment()
        )
    }
}