/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bagusmerta.utility

import android.app.Activity
import android.content.res.Configuration
import android.os.Build
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import coil.load
import coil.request.CachePolicy
import com.bagusmerta.utility.databinding.ColorToastBinding
import java.text.SimpleDateFormat
import java.util.Locale
import kotlin.random.Random

fun ImageView.loadCoilImageHQ(url: String?){
    this.load("${BuildConfig.POSTER_URL_HQ}${url}"){
        diskCachePolicy(CachePolicy.ENABLED)
        error(R.drawable.ic_baseline_broken_image_24)
        crossfade(true)
    }
}

fun ImageView.loadCoilImage(url: String?){
    this.load("${BuildConfig.POSTER_URL}${url}"){
        diskCachePolicy(CachePolicy.ENABLED)
        error(R.drawable.ic_baseline_broken_image_24)
        crossfade(true)
    }
}

fun formatMediaDateYear(date: String?): String? {
    if(!date.isNullOrEmpty()) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.parse(date)
            ?.let { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) }
    } else {
        return "Unknown"
    }
}

fun formatMediaDateMonth(date: String?): String? {
    if(!date.isNullOrEmpty()) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.parse(date)
            ?.let { SimpleDateFormat("MMM yyyy", Locale.getDefault()).format(it) }
    } else {
        return "Unknown"
    }
}

fun View.makeVisible(){
    visibility = View.VISIBLE
}

fun View.makeGone(){
    visibility = View.GONE
}

fun Activity.makeErrorToast(message: String){
    val binding =  ColorToastBinding.inflate(LayoutInflater.from(this))
    val layout = binding.root
    val toast = Toast(this.application)

    binding.let {
        it.colorToastImage.setImageDrawable(ContextCompat.getDrawable(this@makeErrorToast, R.drawable.ic_error_toast))
        startAnimation(this, it.colorToastImage)
        it.colorToastView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.error)
        it.colorToastText.text = "Error"
        it.colorToastDescription.text = message
    }

    startTimer(5000L, toast)
    toast.setGravity(80, 0, 100) // bottom
    toast.setView(layout)
    toast.show()
}

fun Activity.makeInfoToast(message: String){
    val binding =  ColorToastBinding.inflate(LayoutInflater.from(this))
    val layout = binding.root
    val toast = Toast(this.application)


    binding.let {
        it.colorToastImage.setImageDrawable(ContextCompat.getDrawable(this@makeInfoToast, R.drawable.ic_info_toast))
        startAnimation(this, it.colorToastImage)
        it.colorToastView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.info)
        it.colorToastText.text = "Info"
        it.colorToastDescription.text = message
    }

    startTimer(5000L, toast)
    toast.setGravity(80, 0, 100) // bottom
    toast.view = layout
    toast.show()
}

fun Activity.makeSuccessToast(message: String){
    val binding =  ColorToastBinding.inflate(LayoutInflater.from(this))
    val layout = binding.root
    val toast = Toast(this.application)

    binding.let {
        it.colorToastImage.setImageDrawable(ContextCompat.getDrawable(this@makeSuccessToast, R.drawable.ic_success_toast))
        startAnimation(this, it.colorToastImage)
        it.colorToastView.backgroundTintList = ContextCompat.getColorStateList(this, R.color.success)
        it.colorToastText.text = "Success"
        it.colorToastDescription.text = message
    }

    startTimer(5000L, toast)
    toast.setGravity(80, 0, 100) // bottom
    toast.view = layout
    toast.show()
}

private fun startTimer(duration: Long, toast: Toast) {
    val timer = object : CountDownTimer(duration, 1000) {
        override fun onTick(millisUntilFinished: Long) {}
        override fun onFinish() {
            toast.cancel()
        }
    }
    timer.start()
}

private fun startAnimation(context: Activity, customToastImage: ImageView) {
    val pulseAnimation = AnimationUtils.loadAnimation(context, R.anim.pulse)
    customToastImage.startAnimation(pulseAnimation)
}

fun Activity.initStatusBar(){
    window.statusBarColor = getColor(R.color.backgroundPrimary)
    val windowInsetsController = WindowCompat.getInsetsController(
        window, window.decorView
    )
    windowInsetsController.isAppearanceLightStatusBars = resources.configuration.uiMode and Configuration.UI_MODE_NIGHT_MASK === Configuration.UI_MODE_NIGHT_NO
}

fun Activity.hideStatusBar(){
    this.window.apply {
        clearFlags(ContextCompat.getColor(context, R.color.translucent))
        addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            setDecorFitsSystemWindows(false)
        } else {
           decorView.systemUiVisibility = View.SYSTEM_UI_FLAG_FULLSCREEN
        }
    }
}

fun Double.toPercentageString(): String {
    val percentage = this * 10
    return "%.2f%%".format(percentage)
}

fun Int.toKFormatString(): String {
    return when {
        this in 1000..999999 -> "%.1fk".format(this / 1000.0)
        this >= 1000000 -> "%.1fm".format(this / 1000000.0)
        else -> this.toString()
    }
}

fun <T> MutableList<T>.findRandom(): T? {
    if(isEmpty()) return null
    val index = Random.nextInt(size)
    return this[index]
}


