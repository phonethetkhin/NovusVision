@file:SuppressLint("SetTextI18n")

package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.adapter.EpisodeAdapter
import com.ptkako.nv.novusvision.adapter.NumberAdapter
import com.ptkako.nv.novusvision.databinding.ActivitySeriesDetailBinding
import com.ptkako.nv.novusvision.model.EpisodeModel
import com.ptkako.nv.novusvision.model.SeriesModel
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.viewmodel.SeriesDetailViewModel
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI

class SeriesDetailActivity : AppCompatActivity(), DIAware {
    override val di: DI by closestDI()
    private val seriesDetailViewModel: SeriesDetailViewModel by kodeinViewModel()
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


        seriesDetailViewModel.getSeasonNumberLiveData(bundle.series_id.toString()).observe(this, Observer { seasonIdList ->
            if (seasonIdList != null && seasonIdList.size > 0) {
                val numberList = ArrayList<String>()
                seasonIdList.forEach()
                { seasonId ->
                    numberList.add(seasonId)
                }
                numberAdapter.submitList(numberList)

                Log.d("seasonNum", numberList.toString())
            }
        })

        val episodeList = ArrayList<EpisodeModel>()
        episodeList.add(EpisodeModel(1, 1, R.drawable.captain_marvel, "Captain Marvel"))
        episodeList.add(EpisodeModel(2, 2, R.drawable.game_of_throne, "Game of Thrones"))
        episodeList.add(EpisodeModel(3, 3, R.drawable.spiderman, "Spiderman"))
        episodeList.add(EpisodeModel(4, 4, R.drawable.superman, "Superman"))
        episodeList.add(EpisodeModel(5, 5, R.drawable.warcraft, "Warcraft"))

        episodeAdapter.submitList(episodeList)

        binding.rcvSeasonNumber.setHasFixedSize(true)
        binding.rcvSeasonNumber.adapter = numberAdapter
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