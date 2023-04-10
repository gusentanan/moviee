package com.bagusmerta.moviee.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.Constants.BANNER_DELAY
import com.bagusmerta.core.utils.Constants.URI_FAVORITE
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityMainBinding
import com.bagusmerta.moviee.presentation.main.adapter.*
import com.bagusmerta.moviee.presentation.search.SearchActivity
import com.bagusmerta.utility.makeErrorToast
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeInfoToast
import com.bagusmerta.utility.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }
    private val upcomingMoviesAdapter: UpcomingMoviesAdapter by lazy { UpcomingMoviesAdapter(this) }
    private val popularMoviesAdapter: PopularMoviesAdapter by lazy { PopularMoviesAdapter(this) }
    private val topRateMoviesAdapter: TopRatedMoviesAdapter by lazy { TopRatedMoviesAdapter(this) }
    private val nowPlayingMoviesAdapter: NowPlayingMoviesAdapter by lazy { NowPlayingMoviesAdapter(this) }
    private val bannerAdapter: BannerAdapter by lazy { BannerAdapter(this) }

    private val mainViewModel: MainViewModel by viewModel()
    private val items = mutableListOf<Moviee>()
    private val upcomingMovieItems = mutableListOf<Moviee>()
    private val popularMovieItems = mutableListOf<Moviee>()
    private val topRatedMovieItems = mutableListOf<Moviee>()
    private val nowPlayingMovieItems = mutableListOf<Moviee>()

    private lateinit var viewPager: ViewPager2

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAppBar()

        initStateObserver()
        initRecyclerBanner()
        initRecyclerView()
        btnStateListener()
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
            getAllMovies()
            getUpcomingMovies()
            getPopularMovies()
            getNowPlayingMovies()
            getTopRatedMovies()
            result.observe(this@MainActivity){
                handleMovieeResult(it)
            }
            upcomingResult.observe(this@MainActivity){
                handleUpcomingMovieResult(it)
            }
            popularResult.observe(this@MainActivity){
                handlePopularMoviesResult(it)
            }
            topRatedResult.observe(this@MainActivity){
                handleTopRatedMoviesResult(it)
            }
            nowPlayingResult.observe(this@MainActivity){
                handleNowPlayingMoviesResult(it)
            }
            loadingState.observe(this@MainActivity){
                handleLoadingState(it)
            }
            errorState.observe(this@MainActivity){
                handleErrorState(it)
            }
        }
    }

    private fun handleNowPlayingMoviesResult(data: List<Moviee>?) {
        nowPlayingMovieItems.clear()
        data?.let { nowPlayingMovieItems.addAll(it) }
        nowPlayingMoviesAdapter.setNowPlayingMovies(nowPlayingMovieItems)
    }

    private fun handleTopRatedMoviesResult(data: List<Moviee>?) {
        topRatedMovieItems.clear()
        data?.let { topRatedMovieItems.addAll(it) }
        topRateMoviesAdapter.setTopRatedMovies(topRatedMovieItems)
    }

    private fun handlePopularMoviesResult(data: List<Moviee>?) {
        popularMovieItems.clear()
        data?.let { popularMovieItems.addAll(it) }
        popularMoviesAdapter.setPopularMovies(popularMovieItems)
    }

    private fun handleUpcomingMovieResult(data: List<Moviee>?) {
        upcomingMovieItems.clear()
        data?.let { upcomingMovieItems.addAll(it) }
        upcomingMoviesAdapter.setUpcomingMoviesItem(upcomingMovieItems)
    }

    private fun handleErrorState(msg: String){
        this.makeErrorToast(msg)
    }

    private fun handleInfoState(msg: String){
        this.makeInfoToast(msg)
    }

    private fun handleMovieeResult(data: List<Moviee>?) {
        items.clear()
        data?.let { items.addAll(it) }
        bannerAdapter.setItems(items)
        mainAdapter.setItems(items)
    }

    private fun initRecyclerBanner(){
        with(binding){
            viewPager = rvNewMovies
            with(viewPager){
                orientation =ViewPager2.ORIENTATION_HORIZONTAL
                adapter = bannerAdapter
                offscreenPageLimit = 3
            }
        }
    }

    private fun initRecyclerView() {
        with(binding){
            rvMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rvMovies.setHasFixedSize(true)
            rvMovies.adapter = mainAdapter

            rvUpcomingMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rvUpcomingMovies.setHasFixedSize(true)
            rvUpcomingMovies.adapter = upcomingMoviesAdapter

            rvPopularMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rvPopularMovies.setHasFixedSize(true)
            rvPopularMovies.adapter = popularMoviesAdapter

            rvTopratedMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rvTopratedMovies.setHasFixedSize(true)
            rvTopratedMovies.adapter = topRateMoviesAdapter

            rvNowplayingMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.HORIZONTAL, false)
            rvNowplayingMovies.setHasFixedSize(true)
            rvNowplayingMovies.adapter = nowPlayingMoviesAdapter
        }

    }

    private var scrollHandler = Handler(Looper.getMainLooper())
    var scrollRunnable = Runnable {
        with(binding) {
            val setCurrent = rvNewMovies.currentItem + 1
            rvNewMovies.currentItem = setCurrent
            if ((setCurrent) == bannerAdapter.itemCount) {
                rvNewMovies.currentItem = 0
            }
        }
    }

    private fun setupBanner() {
        val compositePageTransformer = CompositePageTransformer()
        val pageMargin = resources.getDimensionPixelOffset(R.dimen.set_margin).toFloat()
        val pageOffset = resources.getDimensionPixelOffset(R.dimen.offset).toFloat()

        compositePageTransformer.addTransformer { page, position ->
            val myOffset: Float = position * -(2 * pageOffset + pageMargin)
            val r = 1 - kotlin.math.abs(position)
            if (viewPager.orientation == ViewPager2.ORIENTATION_HORIZONTAL) {
                if (ViewCompat.getLayoutDirection(viewPager) == ViewCompat.LAYOUT_DIRECTION_RTL) {
                    page.translationX = -myOffset
                } else {
                    page.translationX = myOffset
                }
            } else {
                page.translationY = myOffset
            }
            page.scaleY = 0.85f + r * 0.15f
        }

        binding.rvNewMovies.apply {
            setPageTransformer(compositePageTransformer)
            registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)
                    scrollHandler.removeCallbacks(scrollRunnable)
                    scrollHandler.postDelayed(scrollRunnable, BANNER_DELAY)
                }
            })
        }
    }

    private fun btnStateListener(){
        binding.apply {
            btnSeeAllNowplaying.setOnClickListener {
                handleInfoState("This feature is currently unavailable")
            }
            btnSeeAllPopular.setOnClickListener {
                handleInfoState("This feature is currently unavailable")
            }
            btnSeeAllRecommend.setOnClickListener {
                handleInfoState("This feature is currently unavailable")
            }
            btnSeeAllToprated.setOnClickListener {
                handleInfoState("This feature is currently unavailable")
            }
            btnSeeAllUpcoming.setOnClickListener {
                handleInfoState("This feature is currently unavailable")
            }
        }
    }

    private fun handleLoadingState(state: Boolean){
        binding.apply {
            mainLoadingShimmer.activityMainLoader.let {
                if(state) it.makeVisible() else it.makeGone()
            }
            tvRecommendMovies.let {
                if (state) it.makeGone() else it.makeVisible()
            }
            tv2RecommendMovies.let {
                if (state) it.makeGone() else it.makeVisible()
            }
            tvPopularMovies.let {
                if(state) it.makeGone() else it.makeVisible()
            }
            tv2PopularMovies.let {
                if(state) it.makeGone() else it.makeVisible()
            }
            tvUpcomingMovies.let {
                if(state) it.makeGone() else it.makeVisible()
            }
            tv2UpcomingMovies.let {
                if(state) it.makeGone() else it.makeVisible()
            }
            tvNowplayingMovies.let {
                if(state) it.makeGone() else it.makeVisible()
            }
            tv2NowplayingMovies.let {
                if(state) it.makeGone() else it.makeVisible()
            }
            tvTopratedMovies.let {
                if (state) it.makeGone() else it.makeVisible()
            }
            tv2TopratedMovies.let {
                if (state) it.makeGone() else it.makeVisible()
            }
            btnSeeAllNowplaying.let {
                if (state) it.makeGone() else it.makeVisible()
            }
            btnSeeAllPopular.let {
                if (state) it.makeGone() else it.makeVisible()
            }
            btnSeeAllRecommend.let {
                if (state) it.makeGone() else it.makeVisible()
            }
            btnSeeAllToprated.let {
                if (state) it.makeGone() else it.makeVisible()
            }
            btnSeeAllUpcoming.let {
                if (state) it.makeGone() else it.makeVisible()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupBanner()
    }

}