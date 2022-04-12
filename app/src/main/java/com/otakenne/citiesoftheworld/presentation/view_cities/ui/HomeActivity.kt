package com.otakenne.citiesoftheworld.presentation.view_cities.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.onNavDestinationSelected
import com.otakenne.citiesoftheworld.R
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class HomeActivity : AppCompatActivity() {
    private lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

//        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
//        navController = navHostFragment.navController
//        NavigationUI.setupActionBarWithNavController(this, navController)
    }

    /**
     * Documentation provided by Android
     */
//    override fun onSupportNavigateUp(): Boolean {
//        return navController.navigateUp() || super.onSupportNavigateUp()
//    }

    /**
     * Documentation provided by Android
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
//        menuInflater.inflate(R.menu.home_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    /**
     * Documentation provided by Android
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
//        val id: Int = item.itemId
//        if (id == R.id.navigateToMapFragment) {
//            return item.onNavDestinationSelected(navController) || super.onOptionsItemSelected(item)
//        }
        return super.onOptionsItemSelected(item)
    }
}