package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import androidx.appcompat.app.ActionBarDrawerToggle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.view.GravityCompat
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.tabs.TabLayout
import com.google.firebase.auth.FirebaseAuth
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ActivityHomeBinding
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.HomeViewModel
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance


class HomeActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()
    private lateinit var toggle: ActionBarDrawerToggle
    private lateinit var navController: NavController
    private val homeViewModel: HomeViewModel by kodeinViewModel()
    private val firebaseAuth: FirebaseAuth by instance()
    private val binding by activityViewBinding(ActivityHomeBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
        setSupportActionBar(binding.include.tlbToolbar)
        homeViewModel.getTabPositionLiveData().observe(this, Observer {
            Log.d("tabpos", it.toString())
            if (it != null) {
                binding.tblHome.getTabAt(it)!!.select()
            }
        })
        val headerView = binding.ngvHome.getHeaderView(0)
        Log.d("auth", firebaseAuth.currentUser?.uid.toString())

        headerView.findViewById<TextView>(R.id.txtAccountGmail).text = firebaseAuth.currentUser!!.email
        headerView.findViewById<TextView>(R.id.txtLogout).setOnClickListener {
            firebaseAuth.signOut()
            startActivity(Intent(this, AuthActivity::class.java))
        }
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.fcvHomeNavHostFragment) as NavHostFragment
        navController = navHostFragment.findNavController()
        supportActionBar!!.title = getString(R.string.app_name)
        settingDrawerToggle()
        navListener()
        binding.tblHome.addOnTabSelectedListener(
            object : TabLayout.OnTabSelectedListener {
                override fun onTabSelected(tab: TabLayout.Tab?) {
                    tabSelected(tab!!.position)
                }

                override fun onTabUnselected(tab: TabLayout.Tab?) {}

                override fun onTabReselected(tab: TabLayout.Tab?) {}
            })
    }

    private fun navListener() {
        binding.ngvHome.setNavigationItemSelectedListener { menuItem ->
            when (menuItem.itemId) {
                R.id.nav_playlist -> {
                    startActivity(Intent(this, PlayListActivity::class.java))
                }
                R.id.nav_history -> {
                    startActivity(Intent(this, HistoryActivity::class.java))
                }
                else -> {
                    closeDrawer()
                }
            }
            false
        }
    }

    private fun tabSelected(position: Int) {
        when (position) {
            0 -> {
                homeViewModel.setTabPositionLiveData(0)
                navController.popBackStack(R.id.homeFragment, true)
                navController.navigate(R.id.homeFragment)
            }
            1 -> {
                homeViewModel.setTabPositionLiveData(1)
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.tvSeriesFragment)
            }
            2 -> {
                homeViewModel.setTabPositionLiveData(2)
                navController.popBackStack(R.id.homeFragment, false)
                navController.navigate(R.id.movieFragment)
            }
        }
    }

    override fun onBackPressed() {
        super.onBackPressed()
        when (navController.currentDestination?.id) {
            R.id.homeFragment -> {
                binding.tblHome.getTabAt(0)!!.select()
            }
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

    /**
     * This method is about closing the drawer.
     *
     * @param drlHome (DrawerLayout)
     */
    private fun closeDrawer() {
        if (binding.drlHome.isDrawerOpen(GravityCompat.START)) {
            binding.drlHome.closeDrawer(GravityCompat.START)

        }
    }

}