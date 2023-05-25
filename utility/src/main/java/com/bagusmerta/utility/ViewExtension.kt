package com.bagusmerta.utility

import android.app.Activity
import android.os.Build
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import androidx.core.content.ContextCompat
import androidx.core.content.res.ResourcesCompat
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import www.sanju.motiontoast.MotionToast
import www.sanju.motiontoast.MotionToastStyle
import java.text.SimpleDateFormat
import java.util.*

fun ImageView.loadImage(url: String?){
    Glide.with(context)
        .load("${BuildConfig.POSTER_URL}${url}")
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .error(R.drawable.ic_baseline_broken_image_24)
        .into(this)
}

fun formatMediaDate(date: String?): String? {
    if(!date.isNullOrEmpty()) {
        val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        return sdf.parse(date)
            ?.let { SimpleDateFormat("yyyy", Locale.getDefault()).format(it) }
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
    MotionToast.let {
        it.createColorToast(
            this@makeErrorToast,
            "Error",
            message,
            MotionToastStyle.ERROR,
            it.GRAVITY_BOTTOM,
            it.LONG_DURATION,
            ResourcesCompat.getFont(this, R.font.helveticabold)
        )
    }
}

fun Activity.makeInfoToast(message: String){
    MotionToast.let {
        it.createColorToast(
            this,
            "Info",
            message,
            MotionToastStyle.INFO,
            it.GRAVITY_BOTTOM,
            it.LONG_DURATION,
            ResourcesCompat.getFont(this, R.font.helveticabold)
        )
    }
}

fun Activity.makeSuccessToast(message: String){
    MotionToast.let {
        it.createColorToast(
            this,
            "Success",
            message,
            MotionToastStyle.SUCCESS,
            it.GRAVITY_BOTTOM,
            it.LONG_DURATION,
            ResourcesCompat.getFont(this, R.font.helveticabold)
        )
    }
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

