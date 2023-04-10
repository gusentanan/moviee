package com.bagusmerta.moviee.presentation.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.moviee.databinding.ItemMainComponentBinding
import com.bagusmerta.moviee.presentation.detail.DetailActivity
import com.bagusmerta.utility.loadImage

class MainAdapter(private val context: Context): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemMainComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadImage(item.posterPath)
                tvMovieRating.text = item.rating.toString()

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val binding = ItemMainComponentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setItems(data: MutableList<Moviee>){
        this.items = data
        notifyDataSetChanged()
    }

}

class UpcomingMoviesAdapter(private val context: Context): RecyclerView.Adapter<UpcomingMoviesAdapter.ViewHolder>(){

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemMainComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadImage(item.posterPath)
                tvMovieRating.text = item.rating.toString()

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): UpcomingMoviesAdapter.ViewHolder {
        val binding = ItemMainComponentBinding.inflate(LayoutInflater.from(context), parent , false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: UpcomingMoviesAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setUpcomingMoviesItem(data: MutableList<Moviee>){
        this.items = data
        notifyDataSetChanged()
    }

}

class TopRatedMoviesAdapter(private val context: Context): RecyclerView.Adapter<TopRatedMoviesAdapter.ViewHolder>(){

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemMainComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadImage(item.posterPath)
                tvMovieRating.text = item.rating.toString()

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainComponentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setTopRatedMovies(data: MutableList<Moviee>){
        this.items = data
        notifyDataSetChanged()
    }

}

class NowPlayingMoviesAdapter(private val context: Context): RecyclerView.Adapter<NowPlayingMoviesAdapter.ViewHolder>(){

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemMainComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadImage(item.posterPath)
                tvMovieRating.text = item.rating.toString()

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainComponentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setNowPlayingMovies(data: MutableList<Moviee>){
        this.items = data
        notifyDataSetChanged()
    }

}

class PopularMoviesAdapter(private val context: Context): RecyclerView.Adapter<PopularMoviesAdapter.ViewHolder>(){

    private var items = mutableListOf<Moviee>()

    inner class ViewHolder(private val binding: ItemMainComponentBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: Moviee){
            binding.apply {
                ivPoster.loadImage(item.posterPath)
                tvMovieRating.text = item.rating.toString()

                itemView.setOnClickListener {
                    context.startActivity(Intent(context, DetailActivity::class.java).apply {
                        putExtra(DetailActivity.MOVIEE, item)
                    })
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = ItemMainComponentBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int {
        return items.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setPopularMovies(data: MutableList<Moviee>){
        this.items = data
        notifyDataSetChanged()
    }

}