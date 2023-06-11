package com.bagusmerta.feature.search.presentation

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.MovieeSearch
import com.bagusmerta.feature.detail.presentation.DetailActivity
import com.bagusmerta.feature.search.databinding.ItemSearchComponentBinding
import com.bagusmerta.feature.search.helpers.HelpersSearch
import com.bagusmerta.utility.formatMediaDate
import com.bagusmerta.utility.loadImage

class SearchAdapter(private val context: Context): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var items = mutableListOf<MovieeSearch>()

    inner class ViewHolder(private val binding: ItemSearchComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: MovieeSearch){
            binding.apply {
                ivPoster.loadImage(item.backdropPath)
                tvSearchMovieTitle.text = item.title
                tvMovieRating.text = String.format("%.1f", item.rating)
                tvMovieYear.text = formatMediaDate(item.releaseDate)
                val genreString =  HelpersSearch.mappingMovieGenreListFromId(item.genreId)
                    .joinToString(" • ") { it.name.toString() }

                tvGenres.text = genreString

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item.id)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val  binding = ItemSearchComponentBinding.inflate(LayoutInflater.from(context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<MovieeSearch>){
        this.items = data
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun clearItems(){
        val size = items.size
        items.clear()
        notifyItemRangeRemoved(0, size)
        notifyDataSetChanged()
    }
}