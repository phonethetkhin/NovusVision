package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.EpisodeAdapter
import com.ptkako.nv.novusvision.adapter.NumberAdapter
import com.ptkako.nv.novusvision.databinding.ActivityMovieDetailBinding
import com.ptkako.nv.novusvision.model.EpisodeModel
import com.ptkako.nv.novusvision.model.MovieModel

class MovieDetailActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivityMovieDetailBinding::inflate)
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var numberAdapter: NumberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include2.tlbToolbar)
        val bundle = intent.getParcelableExtra<MovieModel>("movie")!!

        supportActionBar!!.title = bundle.movie_name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        episodeAdapter = EpisodeAdapter(this)
        numberAdapter = NumberAdapter(this)
        setBinding(bundle)


        val episodeList = ArrayList<EpisodeModel>()
        episodeList.add(EpisodeModel(1, 1, R.drawable.captain_marvel, "Captain Marvel"))
        episodeList.add(EpisodeModel(2, 2, R.drawable.game_of_throne, "Game of Thrones"))
        episodeList.add(EpisodeModel(3, 3, R.drawable.spiderman, "Spiderman"))
        episodeList.add(EpisodeModel(4, 4, R.drawable.superman, "Superman"))
        episodeList.add(EpisodeModel(5, 5, R.drawable.warcraft, "Warcraft"))

        val numberList = ArrayList<String>()
        numberList.add("1")
        numberList.add("2")
        numberList.add("3")
        numberList.add("4")
        numberList.add("5")
        numberList.add(">>")

        numberAdapter.submitList(numberList)
        episodeAdapter.submitList(episodeList)
    }

    private fun setBinding(bundle: MovieModel) = with(binding)
    {
        Glide.with(this@MovieDetailActivity).load(bundle.movie_cover_photo).into(binding.imgMovieCover)
        Glide.with(this@MovieDetailActivity).load(bundle.movie_photo).into(binding.imgMoviePhoto)
        txtMoviesTitle.text = bundle.movie_name
        //txtContentRating.text = bundle.cotent_rating
        txtGenre.text = bundle.genres
        txtCountry.text = bundle.country
        //txtProductionYear.text = bundle.production_year
        txtDuration.text = bundle.duration
        txtRating.text = bundle.popularity
        txtLanguage.text = bundle.language
        txtSubtitle.text = bundle.subtitle
        txtStarring.text = bundle.casts
        txtDescription.text = bundle.overview


        rcvSeasonNumber.setHasFixedSize(true)
        rcvSeasonNumber.adapter = numberAdapter

        rcvEpisode.setHasFixedSize(true)
        rcvEpisode.adapter = episodeAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}