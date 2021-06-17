package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.lifecycle.lifecycleScope
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.common.PERMISSION_REQUEST_CODE
import com.ptkako.nv.novusvision.databinding.ActivitySplashBinding
import com.ptkako.nv.novusvision.utility.PREF_GMAIL
import com.ptkako.nv.novusvision.utility.getStringPref
import com.ptkako.nv.novusvision.utility.showToast
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class SplashActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivitySplashBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)


        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.M) {
            requestPermission()
        } else {
            goToNextPage()
        }

    }

    private fun goToNextPage() {
        lifecycleScope.launch {
            delay(1000)
            val gmail = getStringPref(this@SplashActivity, PREF_GMAIL, PREF_GMAIL, "")
            if(gmail=="" || gmail == null) {
                startActivity(Intent(this@SplashActivity, AuthActivity::class.java))
                finish()
            }else{
                startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
                finish()
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun requestPermission() {

        if (checkSelfPermission(Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
            checkSelfPermission(Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
        ) {
            goToNextPage()
        } else {
            requestPermissions(
                arrayOf(
                    Manifest.permission.READ_EXTERNAL_STORAGE,
                    Manifest.permission.WRITE_EXTERNAL_STORAGE
                ), PERMISSION_REQUEST_CODE
            )
        }
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                goToNextPage()
            } else {
                showToast(getString(R.string.permission_denied))
            }
        } else {
            super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        }
    }
}
