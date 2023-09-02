package com.bagusmerta.utility.extensions

import android.app.Activity
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.animation.AnimationUtils
import android.widget.ImageView
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.bagusmerta.utility.R
import com.bagusmerta.utility.databinding.ColorToastBinding


fun Activity.makeErrorToast(message: String){
    val binding =  ColorToastBinding.inflate(LayoutInflater.from(this))
    val layout = binding.root
    val toast = Toast(this.application)

    binding.let {
        it.colorToastImage.setImageDrawable(ContextCompat.getDrawable(this@makeErrorToast,
            R.drawable.ic_error_toast
        ))
        startAnimation(this, it.colorToastImage)
        it.colorToastView.backgroundTintList = ContextCompat.getColorStateList(this,
            com.bagusmerta.common_ui.R.color.error
        )
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
        it.colorToastImage.setImageDrawable(ContextCompat.getDrawable(this@makeInfoToast,
            R.drawable.ic_info_toast
        ))
        startAnimation(this, it.colorToastImage)
        it.colorToastView.backgroundTintList = ContextCompat.getColorStateList(this,
            com.bagusmerta.common_ui.R.color.info
        )
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
        it.colorToastImage.setImageDrawable(ContextCompat.getDrawable(this@makeSuccessToast,
            R.drawable.ic_success_toast
        ))
        startAnimation(this, it.colorToastImage)
        it.colorToastView.backgroundTintList = ContextCompat.getColorStateList(this,
            com.bagusmerta.common_ui.R.color.success
        )
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