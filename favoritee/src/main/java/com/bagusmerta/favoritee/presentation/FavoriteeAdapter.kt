package com.bagusmerta.favoritee.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.favoritee.databinding.ItemFavoriteeBinding
import com.bagusmerta.moviee.presentation.detail.DetailActivity
import com.bagusmerta.utility.loadImage

class FavoriteeAdapter(private val context: Context): RecyclerView.Adapter<FavoriteeAdapter.ViewHolder>() {

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemFavoriteeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivFavMovie.loadImage(item.posterPath)
                ivFavMovieBackdrop.loadImage(item.backdropPath)
                tvFavMovieTitle.text = item.title
                tvFavMovieOverview.text = item.overview

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FavoriteeAdapter.ViewHolder {
        val binding = ItemFavoriteeBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: FavoriteeAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size


    @SuppressLint("NotifyDataSetChanged")
    fun setFavoriteItem(data: MutableList<Moviee>){
        this.items = data
        notifyDataSetChanged()
    }
}