package com.bagusmerta.moviee.utils

import android.app.Activity
import android.view.View
import android.widget.ImageView
import android.widget.Toast
import com.bagusmerta.core.utils.Constants.IMAGE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.google.android.material.floatingactionbutton.FloatingActionButton

fun ImageView.loadImage(url: String?){
    Glide.with(context)
        .load("${IMAGE_URL}${url}")
        .diskCacheStrategy(DiskCacheStrategy.ALL)
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