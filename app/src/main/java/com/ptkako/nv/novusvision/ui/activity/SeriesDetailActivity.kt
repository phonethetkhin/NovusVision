@file:SuppressLint("SetTextI18n")

package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.bumptech.glide.Glide
import com.google.firebase.firestore.FirebaseFirestore
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
import org.kodein.di.instance

class SeriesDetailActivity : AppCompatActivity(), DIAware {
    override val di: DI by closestDI()
    private val seriesDetailViewModel: SeriesDetailViewModel by kodeinViewModel()
    private val fireStore: FirebaseFirestore by instance()
    private val binding by activityViewBinding(ActivitySeriesDetailBinding::inflate)
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var numberAdapter: NumberAdapter
    var seasonId = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include3.tlbToolbar)
        val bundle = intent.getParcelableExtra<SeriesModel>("series")!!

        supportActionBar!!.title = bundle.movie_name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        episodeAdapter = EpisodeAdapter(this)
        numberAdapter = NumberAdapter(this)
        setVisibility()
        observeSeasonIds(bundle)
        setBinding(bundle)

    }

    private fun observeSeasonIds(bundle: SeriesModel) {
        seriesDetailViewModel.getSeasonIdLiveData(bundle.series_id.toString()).observe(this, Observer {
            val numberList = ArrayList<String>()
            it.forEach()
            { seasonId ->
                numberList.add(seasonId)
            }
            numberAdapter.submitList(numberList)
            observeEpisodeList(bundle)
        })
    }

    private fun observeEpisodeList(bundle: SeriesModel) {
        seriesDetailViewModel.getEpisodeListLiveData(bundle.series_id.toString(), seasonId.toString()).observe(this, Observer {
            val episodeList = ArrayList<EpisodeModel>()
            it.forEach()
            { episode ->
                episodeList.add(episode)
            }
            episodeAdapter.submitList(episodeList)
            setVisibility()
        })

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
        binding.rcvSeasonNumber.setHasFixedSize(true)
        binding.rcvSeasonNumber.adapter = numberAdapter
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun setVisibility() = with(binding) {
        pgbSeriesDetail.visibility = seriesDetailViewModel.pgbSeriesDetail
        nsvSeriesDetail.visibility = seriesDetailViewModel.nsvSeriesDetail
    }
}