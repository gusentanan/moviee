package com.bagusmerta.feature.allmovie.presentation

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.feature.allmovie.databinding.ActivityAllMovieBinding
import com.bagusmerta.feature.allmovie.helpers.HelpersAllMovie
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
        val key = intent.getIntExtra(IDENTIFIER, 0)
        when(key){
             1 -> {
                handleTitlePage(HelpersAllMovie.findMovieSection(key).toString())
                allMovieViewModel.getAllMovies()
            }
            2 -> {
                handleTitlePage(HelpersAllMovie.findMovieSection(key).toString())
                allMovieViewModel.getUpcomingMovies()
            }
            3 -> {
                handleTitlePage(HelpersAllMovie.findMovieSection(key).toString())
                allMovieViewModel.getPopularMovies()
            }
            4 -> {
                handleTitlePage(HelpersAllMovie.findMovieSection(key).toString())
                allMovieViewModel.getTopRatedMovies()
            }
            5 -> {
                handleTitlePage(HelpersAllMovie.findMovieSection(key).toString())
                allMovieViewModel.getNowPlayingMovies()
            }
        }

        with(allMovieViewModel){
            result.observe(this@AllMovieActivity){
                runOnUiThread {
                    handleMovieeResult(it)
                }
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
                if (state) it.makeVisible() else it.makeGone()
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