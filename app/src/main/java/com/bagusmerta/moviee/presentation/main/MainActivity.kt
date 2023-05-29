package com.bagusmerta.moviee.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.ViewPager2
import com.bagusmerta.core.domain.model.HomeFeed
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.Constants.URI_FAVORITE
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityMainBinding
import com.bagusmerta.moviee.helpers.Helpers
import com.bagusmerta.moviee.presentation.detail.DetailActivity
import com.bagusmerta.moviee.presentation.main.adapter.MainAdapter
import com.bagusmerta.moviee.presentation.search.SearchActivity
import com.bagusmerta.utility.*
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }

    private val mainViewModel: MainViewModel by viewModel()
    private val items = mutableListOf<HomeFeed>()
    private val bannerItems = mutableListOf<Moviee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAppBar()

        initStateObserver()
        initRecyclerView()
    }

    private fun initAppBar(){
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorSecondaryDark)
        binding.apply {
            btnFavorite.setOnClickListener{
                val uriFavorite = Uri.parse(URI_FAVORITE)
                startActivity(Intent(Intent.ACTION_VIEW, uriFavorite))
            }
            cvSearch.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }
        }
    }

    private fun initStateObserver() {
        with(mainViewModel){
            getBannerMovies()
            getAllFeed()

            resultBanner.observe(this@MainActivity){
                it?.let { data -> handleBannerResult(data) }
            }
            resultAllFeed.observe(this@MainActivity){
                handleMovieeResult(it)
            }
            loadingState.observe(this@MainActivity){
                handleLoadingState(it)
            }
            errorState.observe(this@MainActivity){
                handleErrorState(it)
            }
        }
    }


    private fun handleInfoState(msg: String){
        this.makeInfoToast(msg)
    }

    private fun handleMovieeResult(data: List<HomeFeed>?) {
        items.clear()
        data?.let { items.addAll(it) }
        data?.let { mainAdapter.setListItems(items) }
    }

    private fun handleBannerResult(data: List<Moviee>) {
        bannerItems.clear()
        data.let { bannerItems.addAll(it) }
        val banner: Moviee = bannerItems.findRandom()!!

        binding.apply {
            if(data.isNotEmpty()){
                tvGenreBanner.makeVisible()
                mbMoreInfoBanner.makeVisible()
            }
            ivBanner.loadHighQualityImage(banner.posterPath)
            tvGenreBanner.text = Helpers.mappingMovieGenreListFromId(banner.genreId)
                .joinToString(" • ") { it.name.toString() }

            mbMoreInfoBanner.setOnClickListener {
                startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.MOVIEE, bannerItems[3].id)
                })
            }
        }
    }

    private fun initRecyclerView() {
        with(binding){
            rvvMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            rvvMovies.setHasFixedSize(true)
            rvvMovies.adapter = mainAdapter
        }

    }

    private fun handleLoadingState(state: Boolean){
        binding.apply {
            mainLoadingShimmer.activityMainLoader.let {
                if(state) it.makeVisible() else it.makeGone()
            }
        }
    }

    private fun handleErrorState(msg: String) {
        Timber.tag("ERROR").e(msg)
        binding.apply {
            mainLoadingShimmer.activityMainLoader.makeGone()
            errorState.root.makeVisible()
            errorState.btnTryAgain.setOnClickListener {
                errorState.root.makeGone()
                with(mainViewModel){
                    getBannerMovies()
                    getAllFeed()
                }
            }
        }
    }

}