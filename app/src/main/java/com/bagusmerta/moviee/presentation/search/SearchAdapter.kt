package com.bagusmerta.moviee.presentation.search

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.appcompat.widget.ThemedSpinnerAdapter.Helper
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.moviee.databinding.ItemSearchComponentBinding
import com.bagusmerta.moviee.helpers.Helpers
import com.bagusmerta.moviee.presentation.detail.DetailActivity
import com.bagusmerta.utility.loadImage

class SearchAdapter(private val context: Context): RecyclerView.Adapter<SearchAdapter.ViewHolder>() {

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemSearchComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadImage(item.backdropPath)
                tvSearchMovieTitle.text = item.title
                tvMovieRating.text = String.format("%.1f", item.rating)
                tvMovieYear.text = Helpers.formatMediaDate(item.releaseDate)

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item.id)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SearchAdapter.ViewHolder {
        val  binding = ItemSearchComponentBinding.inflate(LayoutInflater.from(context), parent,false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: SearchAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<Moviee>){
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