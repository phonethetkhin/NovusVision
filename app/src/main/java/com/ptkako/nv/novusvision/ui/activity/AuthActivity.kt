package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.ptkako.nv.novusvision.databinding.ActivityAuthBinding

class AuthActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivityAuthBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
    }
}