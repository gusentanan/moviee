package com.bagusmerta.moviee.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.Constants.URI_FAVORITE
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityMainBinding
import com.bagusmerta.moviee.presentation.main.adapter.BannerAdapter
import com.bagusmerta.moviee.presentation.main.adapter.MainAdapter
import com.bagusmerta.moviee.presentation.search.SearchActivity
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeToast
import com.bagusmerta.utility.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }
    private val bannerAdapter: BannerAdapter by lazy { BannerAdapter(this) }
    private val mainViewModel: MainViewModel by viewModel()
    private val items = mutableListOf<Moviee>()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAppBar()

        initStateObserver()
        initRecyclerBanner()
        initRecyclerView()

    }

    private fun initAppBar(){
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorSecondaryDark)

        findViewById<ImageView>(R.id.btn_favorite).setOnClickListener {
            val uriFavorite = Uri.parse(URI_FAVORITE)
            startActivity(Intent(Intent.ACTION_VIEW, uriFavorite))
        }
        findViewById<CardView>(R.id.cv_search).setOnClickListener {
            startActivity(Intent(this, SearchActivity::class.java))
        }
    }

    private fun initStateObserver() {
        with(mainViewModel){
            getAllMovies()
            result.observe(this@MainActivity){
                handleMovieeResult(it)
            }
            loadingState.observe(this@MainActivity){
                handleLoadingState(it)
            }
            errorState.observe(this@MainActivity){
                handleErrorState(it)
            }
            emptyState.observe(this@MainActivity){
                handleEmptyState(it)
            }
        }
    }

    private fun handleErrorState(msg: String){
        this.makeToast(msg)
    }

    private fun handleMovieeResult(data: List<Moviee>?) {
        items.clear()
        data?.let { items.addAll(it) }
        bannerAdapter.setItems(items)
        mainAdapter.setItems(items)
    }

    private fun initRecyclerBanner(){
        with(binding){
            rvNewMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rvNewMovies.adapter = bannerAdapter
        }
    }

    private fun initRecyclerView() {
        with(binding){
            rvMovies.layoutManager = GridLayoutManager(this@MainActivity, 2)
            rvMovies.setHasFixedSize(true)
            rvMovies.adapter = mainAdapter
        }
    }

    private fun handleEmptyState(state: Boolean){
        binding.apply {
            lottieEmptyRes.root.let {
                if(state) it.makeVisible() else it.makeGone()
            }
            tvNewMovie.let {
                if(state) it.makeGone() else it.makeVisible()
            }
            tvListMovie.let {
                if (state) it.makeGone() else it.makeVisible()
            }
        }
    }

    private fun handleLoadingState(state: Boolean){
        binding.apply {
            progressBar.let {
                if (state) it.makeVisible() else it.makeGone()
            }
            tvNewMovie.let {
                if(state) it.makeGone() else it.makeVisible()
            }
            tvListMovie.let {
                if (state) it.makeGone() else it.makeVisible()
            }
        }
    }
}