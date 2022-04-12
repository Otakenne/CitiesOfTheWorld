package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import androidx.navigation.NavController
import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.filters.MediumTest
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.otakenne.citiesoftheworld.R
import com.otakenne.citiesoftheworld.launchFragmentInHiltContainer
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mockito.mock

@MediumTest
@ExperimentalCoroutinesApi
@HiltAndroidTest
//@RunWith(AndroidJUnit4ClassRunner::class)
class MapFragmentTest {
//    @get:Rule
//    val activityRule = ActivityScenarioRule(HomeActivity::class.java)

    @get:Rule
    var hiltRule = HiltAndroidRule(this)

    @Before
    fun setup() {
        hiltRule.inject()
    }

    @Test
    fun frag() {
        val navController = mock(NavController::class.java)
        launchFragmentInHiltContainer<MapFragment> {

        }
    }
}