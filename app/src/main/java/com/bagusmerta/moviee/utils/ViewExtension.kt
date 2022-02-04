package com.bagusmerta.moviee.utils

import android.widget.ImageView
import com.bagusmerta.core.utils.Constants.IMAGE_URL
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy

fun ImageView.loadImage(url: String?){
    Glide.with(context)
        .load("${IMAGE_URL}${url}")
        .diskCacheStrategy(DiskCacheStrategy.ALL)
        .into(this)
}