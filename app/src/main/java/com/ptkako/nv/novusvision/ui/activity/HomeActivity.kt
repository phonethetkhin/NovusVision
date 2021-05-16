package com.ptkako.nv.novusvision.ui.activity

import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.Toolbar
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var tlbToolbar: Toolbar
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var binding: ActivityHomeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)
        tlbToolbar = findViewById(R.id.tlbToolbar)
        setSupportActionBar(tlbToolbar)
        supportActionBar!!.title = getString(R.string.app_name)
        settingDrawerToggle()
        binding.tblHomeTab.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabSelected(tab!!)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
    }

    private fun tabSelected(tab: TabLayout.Tab) {
        val navController = findNavController(R.id.nav_host_fragment)
        when (tab.position) {
            0 -> {
                navController.popBackStack(R.id.homeFragment, true)
                navController.navigate(R.id.homeFragment)
            }
            1 -> {
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.tvSeriesFragment)
            }
            2 -> {
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.movieFragment)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        val currentFragment = findNavController(R.id.nav_host_fragment).currentDestination?.id
        if (currentFragment == R.id.homeFragment) {
            binding.tblHomeTab.getTabAt(0)!!.select()
        }
    }

    private fun settingDrawerToggle() {
        //setting the drawer toggle
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toggle = ActionBarDrawerToggle(
                this, binding.drlHome, tlbToolbar, R.string.open, R.string.close
            )

            binding.drlHome.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

}