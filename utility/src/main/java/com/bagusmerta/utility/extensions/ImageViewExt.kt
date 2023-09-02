package com.bagusmerta.utility.extensions

import android.widget.ImageView
import coil.load
import coil.request.CachePolicy
import com.bagusmerta.utility.BuildConfig
import com.bagusmerta.utility.R

fun ImageView.loadCoilImageHQ(url: String?){
    this.load("${BuildConfig.POSTER_URL_HQ}${url}"){
        diskCachePolicy(CachePolicy.ENABLED)
        error(com.bagusmerta.common_ui.R.drawable.ic_baseline_broken_image_24)
        crossfade(true)
    }
}

fun ImageView.loadCoilImage(url: String?){
    this.load("${BuildConfig.POSTER_URL}${url}"){
        diskCachePolicy(CachePolicy.ENABLED)
        error(com.bagusmerta.common_ui.R.drawable.ic_baseline_broken_image_24)
        crossfade(true)
    }
}