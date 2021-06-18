@file:SuppressLint("SetTextI18n")

package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import com.bumptech.glide.Glide
import com.google.firebase.auth.FirebaseAuth
import com.ptkako.nv.novusvision.adapter.EpisodeAdapter
import com.ptkako.nv.novusvision.adapter.NumberAdapter
import com.ptkako.nv.novusvision.databinding.ActivitySeriesDetailBinding
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.model.EpisodeModel
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.utility.showToast
import com.ptkako.nv.novusvision.viewmodel.MovieDetailViewModel
import com.ptkako.nv.novusvision.viewmodel.SeriesDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DI
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance

class SeriesDetailActivity : AppCompatActivity(), DIAware {
    override val di: DI by closestDI()
    private val seriesDetailViewModel: SeriesDetailViewModel by kodeinViewModel()
    private val movieDetailViewModel: MovieDetailViewModel by kodeinViewModel()
    private val binding by activityViewBinding(ActivitySeriesDetailBinding::inflate)
    private lateinit var episodeAdapter: EpisodeAdapter
    private lateinit var numberAdapter: NumberAdapter
    val firebaseAuth: FirebaseAuth by instance()
    lateinit var bundle: MovieModel
    var documentID = ""


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include3.tlbToolbar)
        bundle = intent.getParcelableExtra("series")!!

        supportActionBar!!.title = bundle.movie_name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        episodeAdapter = EpisodeAdapter(this, bundle.movie_name)
        numberAdapter = NumberAdapter(this, seriesDetailViewModel)
        setVisibility()
        observeSeasonIds()
        setBinding()
        observeDocumentID()

    }

    private fun observeSeasonIds() {
        seriesDetailViewModel.getSeasonIdLiveData(bundle.series_id.toString()).observe(this, {
            val numberList = ArrayList<String>()
            it.forEach()
            { seasonId ->
                numberList.add(seasonId)
            }
            numberAdapter.submitList(numberList)
            observeEpisodeList()
        })
    }

    fun observeEpisodeList() {
        seriesDetailViewModel.getEpisodeListLiveData(bundle.series_id.toString()).observe(this, {
            Log.d("sid", "${seriesDetailViewModel.seasonId}")
            Log.d("sid", "$it")
            val episodeList = ArrayList<EpisodeModel>()
            it.forEach()
            { episode ->
                episodeList.add(episode)
            }
            episodeAdapter.submitList(episodeList)
            setVisibility()
        })

    }

    private fun setBinding() = with(binding)
    {
        Glide.with(this@SeriesDetailActivity).load(bundle.movie_cover_photo).into(binding.imgMovieCover)
        Glide.with(this@SeriesDetailActivity).load(bundle.movie_photo).into(binding.imgMoviePhoto)
        imgMovieCover.setOnClickListener {
            val intent = Intent(this@SeriesDetailActivity, VideoStreamingActivity::class.java)
            intent.putExtra("videopath", bundle.trailer_video_path)
            intent.putExtra("title", bundle.movie_name)
            startActivity(intent)
        }

        btnTrailer.setOnClickListener {
            val intent = Intent(this@SeriesDetailActivity, VideoStreamingActivity::class.java)
            intent.putExtra("videopath", bundle.trailer_video_path)
            intent.putExtra("title", bundle.movie_name)
            startActivity(intent)
        }
        btnAddToPlaylist.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val playlist = hashMapOf("movie_id" to documentID, "user_id" to firebaseAuth.currentUser!!.uid)
                val existing = movieDetailViewModel.checkExisting(documentID, firebaseAuth.currentUser!!.uid)
                Log.d("userdafsd", existing.toString())
                Log.d("userdafsd", "$documentID, ${firebaseAuth.currentUser!!.uid}")
                if (existing == null) {
                    movieDetailViewModel.addPlayList(playlist)
                } else {
                    when (existing) {
                        is Exception -> showToast(existing.localizedMessage)
                        is String -> showToast("Already added to playlist")
                    }
                }
            }
        }
        txtMoviesTitle.text = bundle.movie_name
        txtContentRating.text = bundle.content_rating
        txtGenre.text = bundle.genres
        txtCountryProductionYear.text = "${bundle.country} (${bundle.production_year} )"
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

    private fun observeDocumentID() {
        movieDetailViewModel.getDocumentIdLiveData(bundle.movie_name).observe(this@SeriesDetailActivity, {
            Log.d("user", it.toString())
            documentID = it
        })
    }
}