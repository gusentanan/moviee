package com.bagusmerta.feature.favoritee.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.feature.detail.presentation.DetailActivity
import com.bagusmerta.feature.favoritee.databinding.ItemFavoriteeBinding
import com.bagusmerta.utility.formatMediaDate
import com.bagusmerta.utility.loadImage
import java.util.*

class FavoriteeAdapter(private val context: Context): RecyclerView.Adapter<FavoriteeAdapter.ViewHolder>() {

    private var items = mutableListOf<MovieeFavorite>()

    inner class ViewHolder(private val binding: ItemFavoriteeBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MovieeFavorite){
            binding.apply {
                ivFavMovie.loadImage(item.backdropPath)
                tvFavMovieTitle.text = item.title
                tvMovieRating.text = String.format("%.1f", item.rating)
                tvMovieYear.text = formatMediaDate(item.releaseDate)
                tvGenres.text = item.genre

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item.id)
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
    fun setFavoriteItem(data: MutableList<MovieeFavorite>){
        this.items = data
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems(){
        items.clear()
        notifyDataSetChanged()
    }
}