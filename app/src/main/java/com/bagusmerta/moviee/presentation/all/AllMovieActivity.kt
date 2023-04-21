package com.bagusmerta.moviee.presentation.all

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.GridLayoutManager
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityAllMovieBinding
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class AllMovieActivity : AppCompatActivity() {

    private val binding: ActivityAllMovieBinding by lazy { ActivityAllMovieBinding.inflate(layoutInflater) }
    private val allMovieAdapter: AllMovieAdapter by lazy { AllMovieAdapter(this) }
    private val allMovieViewModel: AllMovieViewModel by viewModel()
    private val items = mutableListOf<Moviee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initBtnBack()

        initStateObserver()
        initRecycleView()
    }

    private fun initBtnBack() {
        binding.btnBackAll.setOnClickListener {
            onBackPressed()
        }
    }

    private fun initRecycleView() {
        with(binding){
            rvAllMovies.layoutManager = GridLayoutManager(this@AllMovieActivity, 3, GridLayoutManager.VERTICAL, false)
            rvAllMovies.setHasFixedSize(true)
            rvAllMovies.adapter = allMovieAdapter
        }
    }


    private fun initStateObserver() {

        when(intent.getStringExtra(IDENTIFIER)){
            "AllMovies" -> {
                handleTitlePage("All Movies")
                allMovieViewModel.getAllMovies()
            }
            "UpcomingMovies" -> {
                handleTitlePage("Upcoming Movies")
                allMovieViewModel.getUpcomingMovies()
            }
            "PopularMovies" -> {
                handleTitlePage("Popular Movies")
                allMovieViewModel.getPopularMovies()
            }
            "TopRatedMovies" -> {
                handleTitlePage("Top Rated Movies")
                allMovieViewModel.getTopRatedMovies()
            }
            "NowPlayingMovies" -> {
                handleTitlePage("Now Playing Movies")
                allMovieViewModel.getNowPlayingMovies()
            }
        }

        with(allMovieViewModel){
            result.observe(this@AllMovieActivity){
                handleMovieeResult(it)
            }
            loadingState.observe(this@AllMovieActivity){
                handleLoadingState(it)
            }
        }
    }

    private fun handleTitlePage(title: String){
        binding.apply {
            tvTitleAll.text = title
        }
    }


    private fun handleLoadingState(state: Boolean) {
        binding.apply {
            allLoadingShimmer.activityAllMovieLoader.let {
                if(state) it.makeVisible() else it.makeGone()
            }
            vDivider.let {
                if(state) it.makeGone() else it.makeVisible()
            }
        }
    }

    private fun handleMovieeResult(data: List<Moviee>?) {
        items.clear()
        data?.let { items.addAll(it) }
        allMovieAdapter.setItems(items)
    }

    companion object{
        const val IDENTIFIER = "Identifier"
    }
}