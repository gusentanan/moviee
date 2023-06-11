package com.bagusmerta.moviee.presentation.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.feature.detail.presentation.DetailActivity
import com.bagusmerta.moviee.databinding.ItemMainComponentBinding
import com.bagusmerta.utility.loadImage

class HorizontalMovieListAdapter(private val context: Context): RecyclerView.Adapter<HorizontalMovieListAdapter.ViewHolder>(){

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemMainComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadImage(item.posterPath)
                tvMovieRating.text = item.rating.toString()

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item.id)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): HorizontalMovieListAdapter.ViewHolder {
        val binding = ItemMainComponentBinding.inflate(LayoutInflater.from(context), parent , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: HorizontalMovieListAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setMoviesItem(data: List<Moviee?>){
        this.items = data as MutableList<Moviee>
        notifyDataSetChanged()
    }

}