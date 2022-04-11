package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.isDisplayed
import androidx.test.espresso.matcher.ViewMatchers.withId
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.otakenne.citiesoftheworld.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest

@RunWith(AndroidJUnit4ClassRunner::class)
@HiltAndroidTest
class ListFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

//    @Rule
//    var hiltRule = HiltAndroidRule(this)

    @Test
    fun test_recycler_view_is_visible() {
        onView(withId(R.id.city_list)).check(matches(isDisplayed()))
    }
}