@file:Suppress("DEPRECATION")
@file:SuppressLint("ClickableViewAccessibility")


package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.annotation.SuppressLint
import android.content.pm.ActivityInfo
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ProgressBar
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.vkay94.dtpv.youtube.YouTubeOverlay
import com.google.android.exoplayer2.*
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.Util
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ActivityVideoStreamingBinding
import com.ptkako.nv.novusvision.utility.*
import com.ptkako.nv.novusvision.viewmodel.MovieDetailViewModel
import kotlinx.coroutines.*
import org.kodein.di.DIAware
import org.kodein.di.android.closestDI
import org.kodein.di.instance


class VideoStreamingActivity : AppCompatActivity(), DIAware {
    override val di by closestDI()
    val firebaseFirestore: FirebaseFirestore by instance()
    val firebaseAuth: FirebaseAuth by instance()

    private var player: SimpleExoPlayer? = null
    private var playWhenReadyLiveData = MutableLiveData(true)
    private var playbackPosition: Long = 0
    private var playbackStateListener: PlaybackStateListener? = null
    private var currentWindow = 0
    var videoPath = ""
    var title = ""
    var documentId = ""
    var firstTime = true
    var titlePosition = 0
    lateinit var exoProgress: ProgressBar
    lateinit var exoPlay: ImageButton
    lateinit var exoPause: ImageButton
    lateinit var exoPrev: ImageButton
    lateinit var exoNext: ImageButton
    lateinit var backArrow: ImageButton
    lateinit var titleText: TextView
    var episodeList: ArrayList<String>? = null
    var titleList: ArrayList<String>? = null
    private val movieDetailViewModel: MovieDetailViewModel by kodeinViewModel()
    private val binding by activityViewBinding(ActivityVideoStreamingBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        playbackStateListener = PlaybackStateListener()
        videoPath = intent.getStringExtra("videopath")!!
        title = intent.getStringExtra("title")!!
        documentId = intent.getStringExtra("documentid")!!
        exoProgress = binding.exoPlayer.findViewById(R.id.exoBuffering)
        titleText = binding.exoPlayer.findViewById(R.id.exo_text)
        exoPause = binding.exoPlayer.findViewById(R.id.exoPause)
        exoPrev = binding.exoPlayer.findViewById(R.id.exoPrev)
        exoNext = binding.exoPlayer.findViewById(R.id.exoNext)
        exoPlay = binding.exoPlayer.findViewById(R.id.exoPLay)
        backArrow = binding.exoPlayer.findViewById(R.id.imbBackArrow)
        episodeList = intent.getStringArrayListExtra("episodelist")?.toCollection(ArrayList())
        titleList = intent.getStringArrayListExtra("titlelist")?.toCollection(ArrayList())
        Log.d("sts", titlePosition.toString())

        if (titleList != null) {
            if (titleList!!.isNotEmpty()) {
                titlePosition = titleList!!.indexOf(title)
                Log.d("sts", titlePosition.toString())
            }
        }
        if (titlePosition == 0) {
            exoPrev.visibility = View.GONE
        } else {
            exoPrev.visibility = View.VISIBLE
        }
        exoPause.setOnClickListener {
            playWhenReadyLiveData.postValue(false)
            exoPause.visibility = View.GONE
            exoPlay.visibility = View.VISIBLE
        }
        exoPlay.setOnClickListener {
            playWhenReadyLiveData.postValue(true)
            exoPause.visibility = View.VISIBLE
            exoPlay.visibility = View.GONE
        }
        backArrow.setOnClickListener {
           backPressed()
        }
        exoPrev.setOnClickListener {
            titlePosition--
            Log.d("sts", titlePosition.toString())
            if (player != null) {
                player!!.previous()
                titleText.text = getTitleByPosition(titlePosition)
            }
        }
        exoNext.setOnClickListener {
            titlePosition++
            Log.d("sts", titlePosition.toString())
            if (player != null) {
                player!!.next()
                titleText.text = getTitleByPosition(titlePosition)
            }
        }
        Log.d("vd", videoPath)
        Log.d("vd", episodeList.toString())

    }

    override fun onBackPressed() {
        Log.d("sequence", "1")
        backPressed()
    }

    private fun backPressed() {
        CoroutineScope(Dispatchers.Main).launch {
            Log.d("sequence", "2")
            withContext(Dispatchers.Main) { if (player != null && player!!.playbackState == ExoPlayer.STATE_READY) history() }
            Log.d("sequence", "9")
            super.onBackPressed()
        }
    }

    private fun getTitleByPosition(position: Int): String {
        return titleList!![position]
    }

    private fun hideUI() {
        binding.exoPlayer.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            hideUI()
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        if (Util.SDK_INT < 24 && player == null) {
            hideUI()
            initializePlayer()
        }
    }

    override fun onPause() {
        super.onPause()
        if (Util.SDK_INT < 24) {
            releasePlayer()
        }
    }

    override fun onStop() {
        Log.d("onDes", "ONSTOP")
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
        super.onStop()
    }

    private suspend fun history() {
        Log.d("sequence", "3")

        coroutineScope {
            Log.d("sequence", "4")

            Log.d("onDes", player.toString())
            val history: HashMap<String, Any> = hashMapOf("movie_id" to documentId, "user_id" to firebaseAuth.currentUser!!.uid,
                "last_played_time" to (player!!.currentPosition / 1000),
                "total_played_time" to getVideoDurationSeconds(player!!),
                "finish" to 0, "last_watch" to getDateString(getDate()))
            val existing = movieDetailViewModel.checkExistingHistory(documentId, firebaseAuth.currentUser!!.uid) as String?
            Log.d("onDes", existing.toString())
            Log.d("sequence", "5")

            if (existing == null) {
                Log.d("onDes", "existing null")
                movieDetailViewModel.addHistory(history)
            } else {
                Log.d("sequence", "6")

                Log.d("onDes", "existing not null")
                Log.d("onDes", "$existing")
                Log.d("onDes", getDateString(getDate()))
                Log.d("onDes", "$player")
                Log.d("onDes", "${player!!.currentPosition / 1000}")
                Log.d("onDes", "${getVideoDurationSeconds(player!!)}")
                movieDetailViewModel.updateHistory(existing, getDateString(getDate()), (player!!.currentPosition / 1000).toString())
            }
            Log.d("sequence", "7")

        }
        Log.d("sequence", "8")

    }

    private fun initializePlayer() {
        if (player == null) {
            val trackSelector = DefaultTrackSelector(this)
            trackSelector.setParameters(
                trackSelector.buildUponParameters().setMaxVideoSizeSd())
            player = SimpleExoPlayer.Builder(this)
                .setTrackSelector(trackSelector)
                .build()
            binding.exoPlayer.player = player

            if (episodeList != null) {
                if (episodeList!!.isNotEmpty()) {
                    episodeList!!.forEach()
                    {
                        player!!.addMediaItem(MediaItem.fromUri(it))
                    }
                    if (firstTime) currentWindow = episodeList!!.indexOf(videoPath)
                }
                titleText.text = title
            } else {
                titleText.text = title
                player!!.addMediaItem(MediaItem.fromUri(videoPath))
            }
            playWhenReadyLiveData.observe(this, Observer {
                Log.d("pwr", "It: $it")
                if (player != null) {
                    player!!.playWhenReady = it
                }
            })
            player!!.seekTo(currentWindow, playbackPosition)
            player!!.addListener(playbackStateListener!!)
            player!!.prepare()
            binding.doubleTapHandler.player(player!!)
            doubleTapListener()
        }
        firstTime = false
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReadyLiveData.postValue(player!!.playWhenReady)
            Log.d("pwr", "It: ${player!!.playWhenReady}")

            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.removeListener(playbackStateListener!!)
            player!!.release()
            player = null
        }

    }

    inner class PlaybackStateListener : Player.EventListener {
        override fun onMediaItemTransition(mediaItem: MediaItem?, reason: Int) {
            if (titlePosition == 0) {
                exoPrev.visibility = View.GONE
            } else {
                exoPrev.visibility = View.VISIBLE
            }
            if (titleList != null) {
                if (titlePosition == (titleList!!.size - 1)) {
                    exoNext.visibility = View.GONE
                } else {
                    exoNext.visibility = View.VISIBLE
                }
            }
        }

        override fun onPlaybackStateChanged(playbackState: Int) {
            when (playbackState) {
                ExoPlayer.STATE_BUFFERING -> {
                    exoProgress.visibility = View.VISIBLE
                    exoPlay.visibility = View.GONE
                    exoPause.visibility = View.GONE
                }

                ExoPlayer.STATE_READY -> {
                    exoProgress.visibility = View.GONE

                    playWhenReadyLiveData.observe(this@VideoStreamingActivity, Observer {
                        when (it) {
                            true -> {
                                exoPlay.visibility = View.GONE
                                exoPause.visibility = View.VISIBLE
                            }
                            false -> {
                                exoPlay.visibility = View.VISIBLE
                                exoPause.visibility = View.GONE
                            }
                        }
                    })

                }

                ExoPlayer.STATE_ENDED -> showToast("Movie Ended")

                ExoPlayer.STATE_IDLE -> Log.d("idle", "error occurred")

            }
        }
    }

    private fun doubleTapListener() {
        binding.doubleTapHandler
            .performListener(object : YouTubeOverlay.PerformListener {
                override fun onAnimationStart() {
                    // Do UI changes when circle scaling animation starts (e.g. hide controller views)
                    binding.doubleTapHandler.visibility = View.VISIBLE
                }

                override fun onAnimationEnd() {
                    // Do UI changes when circle scaling animation starts (e.g. show controller views)
                    binding.doubleTapHandler.visibility = View.GONE
                }
            })
    }


}