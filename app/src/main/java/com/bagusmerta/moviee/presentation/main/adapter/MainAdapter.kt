package com.bagusmerta.moviee.presentation.main.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bagusmerta.core.domain.model.HomeFeed
import com.bagusmerta.feature.allmovie.presentation.AllMovieActivity
import com.bagusmerta.moviee.databinding.ItemHorizontalMovieListBinding

class MainAdapter(private val context: Context): RecyclerView.Adapter<MainAdapter.ViewHolder>() {

    private var items = mutableListOf<HomeFeed>()

    inner class ViewHolder(private val binding: ItemHorizontalMovieListBinding): RecyclerView.ViewHolder(binding.root){
        fun bind(item: HomeFeed){
            binding.apply {
                tvRecommendMovies.text = item.feedTitle
                tv2RecommendMovies.text = item.feedSubHeader
                btnSeeAllRecommend.setOnClickListener {
                    context.startActivity(Intent(context, AllMovieActivity::class.java).apply {
                        putExtra(AllMovieActivity.IDENTIFIER, item.movieSection)
                    })
                }

                rvMovies.setHasFixedSize(true)

                HorizontalMovieListAdapter(context).let {
                    rvMovies.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
                    rvMovies.adapter = it
                    it.setMoviesItem(item.listMovie)
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MainAdapter.ViewHolder {
        val binding = ItemHorizontalMovieListBinding.inflate(LayoutInflater.from(context), parent, false)
        return ViewHolder(binding)
    }

    override fun onBindViewHolder(holder: MainAdapter.ViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount(): Int = items.size

    @SuppressLint("NotifyDataSetChanged")
    fun setListItems(data: MutableList<HomeFeed>){
        this.items = data
        notifyDataSetChanged()
    }

}

