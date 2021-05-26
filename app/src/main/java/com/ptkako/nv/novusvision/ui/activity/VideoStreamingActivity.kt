package com.ptkako.nv.novusvision.ui.activity

import activityViewBinding
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.ptkako.nv.novusvision.R
import com.ptkako.nv.novusvision.databinding.ActivityVideoStreamingBinding

class VideoStreamingActivity : AppCompatActivity() {
    private val binding by activityViewBinding(ActivityVideoStreamingBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_streaming)

       /* val videoPath = "https://firebasestorage.googleapis.com/v0/b/novus-vision.appspot.com/o/y2mate.is%20-%20Game%20of%20Thrones%20%20Official%20Series%20Trailer%20(HBO)-KPLWWIOCOOQ-1080p-1621600173720.mp4?alt=media&token=719e14f0-1bff-4e5a-91d3-9d897d332ae0"
        Log.d("vd", videoPath)
        binding.vdvMainVideoStreaming.setVideoPath(videoPath)
        binding.vdvMainVideoStreaming.requestFocus()
        binding.vdvMainVideoStreaming.start()*/

    }
}