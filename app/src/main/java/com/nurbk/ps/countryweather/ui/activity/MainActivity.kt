package com.nurbk.ps.countryweather.ui.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI
import com.nurbk.ps.countryweather.R
import com.nurbk.ps.countryweather.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var mBinding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        setSupportActionBar(mBinding.toolbar)

        val navHostFragment = supportFragmentManager
            .findFragmentById(R.id.fragment_nav_host_home) as NavHostFragment?

        val navController = navHostFragment!!.navController

        val appBarConfiguration = AppBarConfiguration.Builder(navController.graph).build()


        NavigationUI.setupWithNavController(
            mBinding.toolbar, navController, appBarConfiguration
        )

        navController.addOnDestinationChangedListener { controller, destination, arguments ->
            when  {
                destination.id != R.id.mainFragment -> {
                    mBinding.toolbar.visibility = View.VISIBLE
                }
                else -> {

                    mBinding.toolbar.visibility = View.GONE
                }
            }

        }
    }
}