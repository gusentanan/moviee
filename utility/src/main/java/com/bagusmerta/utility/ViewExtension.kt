package com.bagusmerta.utility

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImage(url: String?){
    Glide.with(context)
        .load("${BuildConfig.POSTER_URL}${url}")
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .error(R.drawable.ic_baseline_broken_image_24)
        .into(this)
}

fun View.makeVisible(){
    visibility = View.VISIBLE
}

fun View.makeGone(){
    visibility = View.GONE
}

fun Activity.makeToast(message: String){
    Toast.makeText(this, message, Toast.LENGTH_SHORT).show()
}
