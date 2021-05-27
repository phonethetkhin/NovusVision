@file:SuppressLint("SetTextI18n")

package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.EpisodeAdapter
import com.ptkako.nv.novusvision.adapter.NumberAdapter
import com.ptkako.nv.novusvision.databinding.ActivitySeriesDetailBinding
import com.ptkako.nv.novusvision.model.EpisodeModel
import com.ptkako.nv.novusvision.model.SeriesModel

class SeriesDetailActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivitySeriesDetailBinding::inflate)
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var numberAdapter: NumberAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include3.tlbToolbar)
        val bundle = intent.getParcelableExtra<SeriesModel>("series")!!

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

    private fun setBinding(bundle: SeriesModel) = with(binding)
    {
        Glide.with(this@SeriesDetailActivity).load(bundle.movie_cover_photo).into(binding.imgMovieCover)
        Glide.with(this@SeriesDetailActivity).load(bundle.movie_photo).into(binding.imgMoviePhoto)
        imgMovieCover.setOnClickListener {
            val intent = Intent(this@SeriesDetailActivity, VideoStreamingActivity::class.java)
            intent.putExtra("videopath", bundle.trailer_video_path)
            startActivity(intent)
        }

        btnTrailer.setOnClickListener {
            val intent = Intent(this@SeriesDetailActivity, VideoStreamingActivity::class.java)
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