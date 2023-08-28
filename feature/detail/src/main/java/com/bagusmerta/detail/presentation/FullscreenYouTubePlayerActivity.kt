package com.bagusmerta.detail.presentation

import androidx.appcompat.app.AppCompatActivity
import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.MotionEvent
import android.view.View
import android.view.WindowInsets
import android.view.WindowManager
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.view.WindowCompat
import com.bagusmerta.feature.detail.databinding.ActivityFullscreenYouTubePlayerBinding
import com.bagusmerta.feature.detail.R
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
        initStatusBar()
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

    @SuppressLint("ObsoleteSdkInt")
    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT in 21..29) {
            window.statusBarColor = Color.TRANSPARENT
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
        } else if (Build.VERSION.SDK_INT >= 30) {
            window.statusBarColor = Color.TRANSPARENT
            // Making status bar overlaps with the activity
            WindowCompat.setDecorFitsSystemWindows(window, false)
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