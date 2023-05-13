package com.bagusmerta.moviee.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bagusmerta.core.domain.model.HomeFeed
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.Constants.BANNER_DELAY
import com.bagusmerta.core.utils.Constants.URI_FAVORITE
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityMainBinding
import com.bagusmerta.moviee.presentation.all.AllMovieActivity
import com.bagusmerta.moviee.presentation.main.adapter.*
import com.bagusmerta.moviee.presentation.search.SearchActivity
import com.bagusmerta.utility.makeErrorToast
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeInfoToast
import com.bagusmerta.utility.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }
    private val bannerAdapter: BannerAdapter by lazy { BannerAdapter(this) }

    private val mainViewModel: MainViewModel by viewModel()
    private val items = mutableListOf<HomeFeed>()
    private val bannerItems = mutableListOf<Moviee>()

    private lateinit var viewPager: ViewPager2

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
                handleBannerResult(it)
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

    private fun handleInfoState(msg: String){
        this.makeInfoToast(msg)
    }

    private fun handleMovieeResult(data: List<HomeFeed>?) {
        items.clear()
        data?.let { items.addAll(it) }
        data?.let { mainAdapter.setListItems(items) }
    }

    private fun handleBannerResult(data: List<Moviee>?) {
        bannerItems.clear()
        data?.let { bannerItems.addAll(it) }
        bannerAdapter.setItems(bannerItems)
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
            rvvMovies.layoutManager = LinearLayoutManager(this@MainActivity, LinearLayoutManager.VERTICAL, false)
            rvvMovies.setHasFixedSize(true)
            rvvMovies.adapter = mainAdapter
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

    private fun handleLoadingState(state: Boolean){
        binding.apply {
            mainLoadingShimmer.activityMainLoader.let {
                if(state) it.makeVisible() else it.makeGone()
            }
        }
    }

    override fun onResume() {
        super.onResume()
        setupBanner()
    }

}