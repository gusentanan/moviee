package com.bagusmerta.moviee.presentation.main

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager2.widget.CompositePageTransformer
import androidx.viewpager2.widget.ViewPager2
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.Constants.BANNER_DELAY
import com.bagusmerta.core.utils.Constants.URI_FAVORITE
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityMainBinding
import com.bagusmerta.moviee.presentation.main.adapter.BannerAdapter
import com.bagusmerta.moviee.presentation.main.adapter.MainAdapter
import com.bagusmerta.moviee.presentation.search.SearchActivity
import com.bagusmerta.utility.makeErrorToast
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }
    private val bannerAdapter: BannerAdapter by lazy { BannerAdapter(this) }
    private val mainViewModel: MainViewModel by viewModel()
    private val items = mutableListOf<Moviee>()

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
        this.makeErrorToast(msg)
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
        }
    }

    private fun handleLoadingState(state: Boolean){
        binding.apply {
            shimmerLoading1.let {
                if(state) it.makeVisible() else it.makeGone()
            }
            shimmerLoading2.let {
                if(state) it.makeVisible() else it.makeGone()
            }
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

    override fun onResume() {
        super.onResume()
        setupBanner()
    }

}