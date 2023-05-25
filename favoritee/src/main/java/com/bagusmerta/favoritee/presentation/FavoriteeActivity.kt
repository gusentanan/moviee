package com.bagusmerta.favoritee.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.favoritee.R
import com.bagusmerta.favoritee.databinding.ActivityFavoriteeBinding
import com.bagusmerta.favoritee.di.favoriteeModule
import com.bagusmerta.utility.makeErrorToast
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import org.koin.core.context.loadKoinModules
import timber.log.Timber


class FavoriteeActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteeBinding by lazy {
        ActivityFavoriteeBinding.inflate(
            layoutInflater
        )
    }
    private val favoriteeViewModel: FavoriteeViewModel by viewModel()
    private val favoriteeAdapter: FavoriteeAdapter by lazy { FavoriteeAdapter(this) }
    private val items = mutableListOf<MovieeFavorite>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.favorite_page_title)

        loadKoinModules(favoriteeModule)
        initBtnBack()
        initObserverState()
        initRecyclerView()
    }

   private fun initBtnBack(){
       binding.btnBack.setOnClickListener { onBackPressed() }
   }

    private fun initRecyclerView() {
        binding.apply {
            rvFavMovie.layoutManager = LinearLayoutManager(this@FavoriteeActivity)
            rvFavMovie.adapter = favoriteeAdapter
        }
    }

    private fun initObserverState() {
        favoriteeViewModel.apply {
            getFavoriteMovies(true)

            loadingState.observe(this@FavoriteeActivity){
                handleLoadingState(it)
            }
            result.observe(this@FavoriteeActivity) {
                it?.let { handleFavoriteMovieResult(it) }
            }
            emptyState.observe(this@FavoriteeActivity) {
                handleEmptyStateResult(it)
            }
            errorState.observe(this@FavoriteeActivity){
                handleErrorState(it)
            }
        }
    }

    private fun handleErrorState(msg: String?) {
        this.makeErrorToast("Oops Something Wrong Happen")
        Timber.e(msg)
    }

    private fun handleLoadingState(state: Boolean) {
        binding.loadingState.apply {
                if(state) makeVisible() else makeGone()
        }
    }

    private fun handleFavoriteMovieResult(data: List<MovieeFavorite>) {
        items.clear()
        items.addAll(data)
        favoriteeAdapter.setFavoriteItem(items)
    }

    private fun handleEmptyStateResult(state: Boolean) {
        binding.emptyState.root.apply {
            if (state) {
                favoriteeAdapter.clearItems()
                makeVisible()
            } else makeGone()
        }
    }

}