package com.bagusmerta.detail.presentation

import android.annotation.SuppressLint
import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.bagusmerta.feature.detail.databinding.ActivityFullscreenYouTubePlayerBinding
import com.bagusmerta.utility.initTransparentStatusBar
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener

/**
 * An example full-screen activity that shows and hides the system UI (i.e.
 * status bar and navigation/system bar) with user interaction.
 */
class FullscreenYouTubePlayerActivity : AppCompatActivity() {

    private val binding: ActivityFullscreenYouTubePlayerBinding by lazy { ActivityFullscreenYouTubePlayerBinding.inflate(layoutInflater) }
    private var _youtubePlayer: YouTubePlayer? = null
    private var youtubePlayerListener: AbstractYouTubePlayerListener? = null

    @SuppressLint("ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        initTransparentStatusBar()

        handleButtonBack()
        val keyVideo = intent.getStringExtra(KEY_TRAILERS)
        if(_youtubePlayer == null) {
            keyVideo?.let { initYouTubePlayer(it) }
        } else {
            keyVideo?.let { _youtubePlayer!!.cueVideo(it, 0f) }
        }
    }

    private fun handleButtonBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(1000) {
                onBackPressedDispatcher.onBackPressed() }
        }
        binding.btnBackYt.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
            finish()
        }
    }

    private fun initYouTubePlayer(keyVideo: String){
        binding.apply {
            youtubePlayerListener = object : AbstractYouTubePlayerListener() {
                override fun onReady(youTubePlayer: YouTubePlayer) {
                    keyVideo.let { key ->
                        _youtubePlayer = youTubePlayer
                        _youtubePlayer!!.loadVideo(key, 0f)
                    }
                }
            }

            ytPlayerView.addYouTubePlayerListener(youtubePlayerListener!!)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        youtubePlayerListener?.let { binding.ytPlayerView.release() }
    }

    companion object {
        const val KEY_TRAILERS = "key_trailers"
    }

}