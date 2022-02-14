package com.bagusmerta.favoritee.presentation

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.favoritee.databinding.ActivityFavoriteeBinding
import com.bagusmerta.favoritee.di.favoriteeModule
import com.bagusmerta.moviee.utils.makeGone
import com.bagusmerta.moviee.utils.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules

class FavoriteeActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteeBinding by lazy { ActivityFavoriteeBinding.inflate(layoutInflater) }
    private val favoriteeViewModel: FavoriteeViewModel by viewModel()
    private val favoriteeAdapter: FavoriteeAdapter by lazy { FavoriteeAdapter(this) }
    private val items = mutableListOf<Moviee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        loadKoinModules(favoriteeModule)
        handleProgressBarState(true)
        initObserverState()
        initRecyclerView()
    }

    private fun initRecyclerView() {
        binding.apply {
            rvFavMovie.layoutManager = LinearLayoutManager(this@FavoriteeActivity)
            rvFavMovie.adapter = favoriteeAdapter
        }
    }

    private fun initObserverState() {
        favoriteeViewModel.apply {
            favoriteMovieList.observe(this@FavoriteeActivity){
                handleProgressBarState(false)
                handleFavoriteMovieResult(it)
            }
        }
    }

    private fun handleFavoriteMovieResult(data: List<Moviee>){
        items.clear()
        items.addAll(data)
        favoriteeAdapter.setFavoriteItem(items)
    }

    private fun handleProgressBarState(state: Boolean) {
        binding.progressBar.let {
            if(state) it.makeVisible() else it.makeGone()
        }
    }
}