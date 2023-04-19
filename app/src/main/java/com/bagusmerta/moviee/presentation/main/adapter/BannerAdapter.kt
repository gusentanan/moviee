package com.bagusmerta.moviee.presentation.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ItemSlideShowBinding
import com.bagusmerta.moviee.presentation.detail.DetailActivity
import com.bagusmerta.utility.loadImage

class BannerAdapter(private val context: Context): RecyclerView.Adapter<BannerAdapter.ViewHolder>() {

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemSlideShowBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivSlide.loadImage(item.backdropPath)
                tvTitleText.text = item.title
                movieeBanner.animation = AnimationUtils.loadAnimation(
                    context, R.anim.fade_transition
                )

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item.id)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemSlideShowBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<Moviee>){
        this.items = data.asSequence().shuffled().take(6).toMutableList()
        notifyDataSetChanged()
    }
}