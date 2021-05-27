package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.google.android.exoplayer2.ExoPlayer
import com.google.android.exoplayer2.MediaItem
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.SimpleExoPlayer
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.util.Util
import com.ptkako.nv.novusvision.databinding.ActivityVideoStreamingBinding

class VideoStreamingActivity : AppCompatActivity() {
    private var player: SimpleExoPlayer? = null
    private var playWhenReady = false
    private var playbackPosition: Long = 0
    private var playbackStateListener: PlaybackStateListener? = null
    private var currentWindow = 0
    var videoPath = ""
    private val binding by activityViewBinding(ActivityVideoStreamingBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        playbackStateListener = PlaybackStateListener()
        videoPath = intent.getStringExtra("videopath")!!
        Log.d("vd", videoPath)

    }

    override fun onStart() {
        super.onStart()
        if (Util.SDK_INT >= 24) {
            initializePlayer()
        }
    }

    override fun onResume() {
        super.onResume()
        // hideSystemUi()
        if (Util.SDK_INT < 24 && player == null) {
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
            val mediaItem = MediaItem.Builder()
                .setUri(videoPath)
                .build()
            binding.exoPlayer.player = player
            player!!.setMediaItem(mediaItem)
            player!!.playWhenReady = playWhenReady
            player!!.seekTo(currentWindow, playbackPosition)
            player!!.addListener(playbackStateListener!!)
            player!!.prepare()
        }
    }

    private fun releasePlayer() {
        if (player != null) {
            playWhenReady = player!!.playWhenReady
            playbackPosition = player!!.currentPosition
            currentWindow = player!!.currentWindowIndex
            player!!.removeListener(playbackStateListener!!)
            player!!.release()
            player = null
        }
    }

    @SuppressLint("InlinedApi")
    private fun hideSystemUi() {
        binding.exoPlayer.systemUiVisibility = (View.SYSTEM_UI_FLAG_LOW_PROFILE
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION)
    }

    private class PlaybackStateListener : Player.EventListener {
        override fun onPlaybackStateChanged(playbackState: Int) {
            val stateString: String = when (playbackState) {
                ExoPlayer.STATE_IDLE -> "ExoPlayer.STATE_IDLE      -"
                ExoPlayer.STATE_BUFFERING -> "ExoPlayer.STATE_BUFFERING -"
                ExoPlayer.STATE_READY -> "ExoPlayer.STATE_READY     -"
                ExoPlayer.STATE_ENDED -> "ExoPlayer.STATE_ENDED     -"
                else -> "UNKNOWN_STATE             -"
            }
            Log.d("tag", "changed state to $stateString")
        }
    }
}