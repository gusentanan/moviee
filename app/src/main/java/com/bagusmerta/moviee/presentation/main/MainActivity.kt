package com.bagusmerta.moviee.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.Constants.URI_FAVORITE
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityMainBinding
import com.bagusmerta.moviee.presentation.main.adapter.BannerAdapter
import com.bagusmerta.moviee.presentation.main.adapter.MainAdapter
import com.bagusmerta.moviee.presentation.search.SearchActivity
import com.bagusmerta.moviee.utils.makeGone
import com.bagusmerta.moviee.utils.makeToast
import com.bagusmerta.moviee.utils.makeVisible
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
            movieeList.observe(this@MainActivity){
                when(it){
                    is Resource.Success -> {
                        binding.progressBar.makeGone()
                        it.data?.let { res -> handleMovieeResult(res) }
                    }
                    is Resource.Error ->  {
                        binding.progressBar.makeGone()
                        handleErrorState()
                    }
                    is Resource.Loading -> binding.progressBar.makeVisible()
                }
            }
        }
    }


    private fun handleErrorState(){
        this@MainActivity.makeToast(getString(R.string.error_something_wrong))
    }

    private fun handleMovieeResult(data: List<Moviee>) {
        items.clear()
        items.addAll(data)
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
}