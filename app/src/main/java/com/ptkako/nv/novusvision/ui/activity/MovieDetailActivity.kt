package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.ptkako.nv.novusvision.databinding.ActivityMovieDetailBinding

class MovieDetailActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivityMovieDetailBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include2.tlbToolbar)
        supportActionBar!!.title = "Game of Thrones"
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}