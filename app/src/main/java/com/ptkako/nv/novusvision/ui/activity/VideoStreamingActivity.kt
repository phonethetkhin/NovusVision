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
import androidx.annotation.Nullable
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import com.github.vkay94.dtpv.youtube.YouTubeOverlay
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.Player.MediaItemTransitionReason
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.Util
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ActivityVideoStreamingBinding
import com.ptkako.nv.novusvision.utility.showToast


class VideoStreamingActivity : AppCompatActivity() {
    private var player: SimpleExoPlayer? = null
    private var playWhenReadyLiveData = MutableLiveData(true)
    private var playbackPosition: Long = 0
    private var playbackStateListener: PlaybackStateListener? = null
    private var currentWindow = 0
    var videoPath = ""
    var title = ""
    lateinit var exoProgress: ProgressBar
    lateinit var exoPlay: ImageButton
    lateinit var exoPause: ImageButton
    lateinit var backArrow: ImageButton
    lateinit var titleText: TextView
    var episodeList: ArrayList<String>? = null
    var titleList: ArrayList<String>? = null
    private val binding by activityViewBinding(ActivityVideoStreamingBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        this.requestedOrientation = ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE
        playbackStateListener = PlaybackStateListener()
        videoPath = intent.getStringExtra("videopath")!!
        title = intent.getStringExtra("title")!!
        exoProgress = binding.exoPlayer.findViewById(R.id.exoBuffering)
        titleText = binding.exoPlayer.findViewById(R.id.exo_text)
        exoPause = binding.exoPlayer.findViewById(R.id.exoPause)
        exoPlay = binding.exoPlayer.findViewById(R.id.exoPLay)
        backArrow = binding.exoPlayer.findViewById(R.id.imbBackArrow)
        episodeList = intent.getStringArrayListExtra("episodelist")?.toCollection(ArrayList())
        titleList = intent.getStringArrayListExtra("titlelist")?.toCollection(ArrayList())
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
            super.onBackPressed()
        }
        Log.d("vd", videoPath)
        Log.d("vd", episodeList.toString())

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
        super.onStop()
        if (Util.SDK_INT >= 24) {
            releasePlayer()
        }
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
                    val position = episodeList!!.indexOf(videoPath)
                    player!!.moveMediaItem(position, 0)
                }
                if (titleList != null) {
                    if (titleList!!.isNotEmpty()) {
                        val position = titleList!!.indexOf(title)
                        titleList!!.removeAt(position)
                        titleList!!.add(0, title)
                    }
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