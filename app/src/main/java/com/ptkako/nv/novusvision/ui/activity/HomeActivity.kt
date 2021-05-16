package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ActivityHomeBinding


class HomeActivity : AppCompatActivity() {
    private lateinit var toggle: ActionBarDrawerToggle
    private val binding by activityViewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include.tlbToolbar)
        supportActionBar!!.title = getString(R.string.app_name)
        settingDrawerToggle()
        binding.tblHome.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabSelected(tab!!)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
    }

    private fun tabSelected(tab: TabLayout.Tab) {
        val navController = findNavController(R.id.fcvHomeNavHostFragment)
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
        val currentFragment = findNavController(R.id.fcvHomeNavHostFragment).currentDestination?.id
        if (currentFragment == R.id.homeFragment) {
            binding.tblHome.getTabAt(0)!!.select()
        }
    }

    private fun settingDrawerToggle() {
        //setting the drawer toggle
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            toggle = ActionBarDrawerToggle(
                this, binding.drlHome, binding.include.tlbToolbar, R.string.open, R.string.close
            )

            binding.drlHome.addDrawerListener(toggle)
            toggle.syncState()
        }
    }

}