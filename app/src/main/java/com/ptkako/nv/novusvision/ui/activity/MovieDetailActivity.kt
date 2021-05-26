@file:SuppressLint("SetTextI18n")

package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.databinding.ActivityMovieDetailBinding
import com.ptkako.nv.novusvision.model.MovieModel

class MovieDetailActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivityMovieDetailBinding::inflate)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include2.tlbToolbar)
        val bundle = intent.getParcelableExtra<MovieModel>("movie")!!

        supportActionBar!!.title = bundle.movie_name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setBinding(bundle)
    }

    private fun setBinding(bundle: MovieModel) = with(binding)
    {
        Glide.with(this@MovieDetailActivity).load(bundle.movie_cover_photo).into(binding.imgMovieCover)
        Glide.with(this@MovieDetailActivity).load(bundle.movie_photo).into(binding.imgMoviePhoto)
        btnTrailer.setOnClickListener {
            val intent = Intent(this@MovieDetailActivity, VideoStreamingActivity::class.java)
            intent.putExtra("videopath", bundle.trailer_video_path)
            startActivity(intent)
        }
        txtMoviesTitle.text = bundle.movie_name
        txtContentRating.text = bundle.content_rating
        txtGenre.text = bundle.genres
        txtCountry.text = bundle.country
        txtProductionYear.text = "(${bundle.production_year})"
        txtDuration.text = bundle.duration
        txtRating.text = bundle.popularity
        txtLanguage.text = bundle.language
        txtSubtitle.text = bundle.subtitle
        txtStarring.text = bundle.casts
        txtDescription.text = bundle.overview
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}