package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.otakenne.citiesoftheworld.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
    }
}