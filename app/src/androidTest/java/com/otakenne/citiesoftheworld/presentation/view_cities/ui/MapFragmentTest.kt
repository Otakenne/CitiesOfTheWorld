package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import androidx.test.espresso.Espresso
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import com.otakenne.citiesoftheworld.R
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4ClassRunner::class)
class MapFragmentTest {
    @get:Rule
    val activityRule = ActivityScenarioRule(HomeActivity::class.java)
}