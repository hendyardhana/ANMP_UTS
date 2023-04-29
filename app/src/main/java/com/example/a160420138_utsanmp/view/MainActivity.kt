package com.example.a160420138_utsanmp.view

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.example.a160420138_utsanmp.R
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.google.android.material.navigation.NavigationView

class MainActivity : AppCompatActivity() {

    private lateinit var navController:NavController
    private lateinit var drawerLayout: DrawerLayout

    companion object{
        val sharedusername = "sharedusername"
    }

    fun SetupMethod(){
        //Setup Drawer
        drawerLayout = findViewById(R.id.drawerLayout)

        //Setup Bottom Nav
        navController = (supportFragmentManager.findFragmentById(R.id.hostFragment) as NavHostFragment).navController

        NavigationUI.setupActionBarWithNavController(this, navController, drawerLayout)

        val navView = findViewById<NavigationView>(R.id.navView)
        NavigationUI.setupWithNavController(navView, navController)

        val bottomNav = findViewById<BottomNavigationView>(R.id.bottomNav)
        bottomNav.setupWithNavController(navController)

//        val shared: SharedPreferences = getSharedPreferences(HomeFragment.sharedusername, Context.MODE_PRIVATE)
//        val username = shared.getString(HomeFragment.sharedusername, "")
//        if(username == ""){
//            bottomNav.visibility = View.GONE
//        }
//        else{
//            bottomNav.visibility = View.VISIBLE
//        }
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        SetupMethod()
    }

    override fun onSupportNavigateUp(): Boolean {
        return NavigationUI.navigateUp(navController, drawerLayout) || super.onSupportNavigateUp()
    }

    override fun onResume() {
        super.onResume()
        SetupMethod()
    }
}