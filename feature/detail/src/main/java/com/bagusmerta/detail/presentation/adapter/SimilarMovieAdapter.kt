package com.bagusmerta.feature.detail.presentation.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.feature.detail.databinding.ItemMainComponentBinding
import com.bagusmerta.utility.loadImage

class SimilarMovieAdapter(private  val context: Context): RecyclerView.Adapter<SimilarMovieAdapter.ViewHolder>() {

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemMainComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadImage(item.posterPath)
                tvMovieRating.text = String.format("%.1f", item.rating)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SimilarMovieAdapter.ViewHolder {
        val binding = ItemMainComponentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: SimilarMovieAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setSimilarMovieItems(data: MutableList<Moviee>){
        this.items = data
        notifyDataSetChanged()
    }

}