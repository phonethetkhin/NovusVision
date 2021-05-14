package com.ptkako.nv.novusvision.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.findNavController
import com.google.android.material.tabs.TabLayout
import com.ptkako.nv.novusvision.R
import kotlinx.android.synthetic.main.activity_home.*


class HomeActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        tblHomeTab.addOnTabSelectedListener(
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
}
