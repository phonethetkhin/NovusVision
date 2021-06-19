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
import com.ptkako.nv.novusvision.databinding.ActivityMovieDetailBinding
import com.ptkako.nv.novusvision.model.MovieModel
import com.ptkako.nv.novusvision.utility.getDate
import com.ptkako.nv.novusvision.utility.getDateString
import com.ptkako.nv.novusvision.utility.kodeinViewModel
import com.ptkako.nv.novusvision.utility.showToast
import com.ptkako.nv.novusvision.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance
import java.util.*

class MovieDetailActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()
    var documentID = ""
    private val movieDetailViewModel: MovieDetailViewModel by kodeinViewModel()
    val firebaseAuth: FirebaseAuth by instance()
    private val binding by activityViewBinding(ActivityMovieDetailBinding::inflate)
    lateinit var bundle: MovieModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        setSupportActionBar(binding.include2.tlbToolbar)
        bundle = intent.getParcelableExtra("movie")!!

        supportActionBar!!.title = bundle.movie_name
        supportActionBar!!.setDisplayHomeAsUpEnabled(true)
        setBinding()
        observeDocumentID()
    }

    private fun setBinding() = with(binding)
    {
        Glide.with(this@MovieDetailActivity).load(bundle.movie_cover_photo).into(binding.imgMovieCover)
        Glide.with(this@MovieDetailActivity).load(bundle.movie_photo).into(binding.imgMoviePhoto)
        imgMovieCover.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val history = hashMapOf("movie_id" to documentID, "user_id" to firebaseAuth.currentUser!!.uid,
                    "last_played_time" to "00:00", "finish" to "0", "last_watch" to getDateString(getDate(),
                        "yyyy-MM-dd'T'HH:mm:ss.SSSZ", "dd-MMM-yyyy hh:mm:ss aaa"))
                val existing = movieDetailViewModel.checkExistingHistory(documentID, firebaseAuth.currentUser!!.uid,
                    "00:00", "0")
                if (existing == null) {
                    movieDetailViewModel.addHistory(history)
                }
                val intent = Intent(this@MovieDetailActivity, VideoStreamingActivity::class.java)
                intent.putExtra("videopath", bundle.full_video_path)
                intent.putExtra("title", bundle.movie_name)
                startActivity(intent)
            }
        }

        btnTrailer.setOnClickListener {
            val intent = Intent(this@MovieDetailActivity, VideoStreamingActivity::class.java)
            intent.putExtra("videopath", bundle.trailer_video_path)
            intent.putExtra("title", bundle.movie_name)
            startActivity(intent)
        }
        btnAddToPlaylist.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val playlist = hashMapOf("movie_id" to documentID, "user_id" to firebaseAuth.currentUser!!.uid)
                val existing = movieDetailViewModel.checkExistingPlaylist(documentID, firebaseAuth.currentUser!!.uid)
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
    }

    private fun observeDocumentID() {
        movieDetailViewModel.getDocumentIdLiveData(bundle.movie_name).observe(this@MovieDetailActivity, {
            Log.d("user", it.toString())
            documentID = it
        })
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            android.R.id.home -> super.onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }
}