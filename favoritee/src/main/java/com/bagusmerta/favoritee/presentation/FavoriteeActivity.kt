package com.bagusmerta.favoritee.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.favoritee.R
import com.bagusmerta.favoritee.databinding.ActivityFavoriteeBinding
import com.bagusmerta.favoritee.di.favoriteeModule
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeVisible
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
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.favorite_page_title)

        loadKoinModules(favoriteeModule)
        initObserverState()
        initRecyclerView()
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    private fun initRecyclerView() {
        binding.apply {
            rvFavMovie.layoutManager = LinearLayoutManager(this@FavoriteeActivity)
            rvFavMovie.adapter = favoriteeAdapter
        }
    }

    private fun initObserverState() {
        favoriteeViewModel.apply {
            getAllFavoritesMovies()
            favoriteState.observe(this@FavoriteeActivity){ state ->
                when(state){
                    is FavoriteMovieeState.EmptyState -> handleEmptyStateResult()
                    is FavoriteMovieeState.GetAllFavoriteMovies -> handleFavoriteMovieResult(state.data)
                }
            }
        }
    }

    private fun handleFavoriteMovieResult(data: List<Moviee>){
        items.clear()
        items.addAll(data)
        favoriteeAdapter.setFavoriteItem(items)
    }

    private fun handleEmptyStateResult(){
        binding.lottieView.root.apply {
            makeVisible()
        }
    }
}