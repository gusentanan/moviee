/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.bagusmerta.moviee.presentation.main

import android.annotation.SuppressLint
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.view.MotionEvent
import android.view.View
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
import android.view.View.SYSTEM_UI_FLAG_LAYOUT_STABLE
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.core.view.WindowCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.HomeFeed
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.DataMapper
import com.bagusmerta.feature.detail.presentation.DetailActivity
import com.bagusmerta.feature.favoritee.presentation.FavoriteeActivity
import com.bagusmerta.feature.search.presentation.SearchActivity
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivityMainBinding
import com.bagusmerta.moviee.presentation.main.adapter.MainAdapter
import com.bagusmerta.utility.findRandom
import com.bagusmerta.utility.loadCoilImageHQ
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeInfoToast
import com.bagusmerta.utility.makeVisible
import com.google.android.material.appbar.AppBarLayout
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import kotlin.math.abs
import kotlin.math.min

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy { ActivityMainBinding.inflate(layoutInflater) }
    private val mainAdapter: MainAdapter by lazy { MainAdapter(this) }

    private val mainViewModel: MainViewModel by viewModel()
    private val items = mutableListOf<HomeFeed>()
    private val bannerItems = mutableListOf<Moviee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initStatusBar()
        initAppBar()
        observerAppBar()

        initStateObserver()
        initRecyclerView()
    }

    private fun initAppBar(){
        binding.apply {
            btnFavorite.setOnClickListener{
                startActivity(Intent(this@MainActivity, FavoriteeActivity::class.java))
            }
            cvSearch.setOnClickListener {
                startActivity(Intent(this@MainActivity, SearchActivity::class.java))
            }
        }
    }

    private fun observerAppBar() {
        binding.apply {
            nestedScroll.setOnScrollChangeListener { _, _, scrollY, _, oldScrollY ->
                if (scrollY > oldScrollY) {  // scroll down
                    materialAppBarLayout.visibility = View.GONE
                } else {
                    if (scrollY < oldScrollY) {  // scroll up
                        materialAppBarLayout.visibility = View.VISIBLE
                        val color = changeBackgroundColorAppBar(
                            ContextCompat.getColor(
                                this@MainActivity,
                                R.color.black_transparent_light
                            ),
                            (min(255, scrollY).toFloat() / 255.0f).toDouble()
                        )
                        materialAppBarLayout.setBackgroundColor(color)
                    }
                }
            }
        }
    }

    @SuppressLint("ObsoleteSdkInt")
    private fun initStatusBar() {
        if (Build.VERSION.SDK_INT in 21..29) {
            window.statusBarColor = Color.TRANSPARENT
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
            window.setFlags(
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS
            )
         } else if (Build.VERSION.SDK_INT >= 30) {
             window.statusBarColor = Color.TRANSPARENT
            // Making status bar overlaps with the activity
            WindowCompat.setDecorFitsSystemWindows(window, false)
         }
    }

    private fun initStateObserver() {
        with(mainViewModel) {
            resultBanner.observe(this@MainActivity) {
                runOnUiThread {
                    it?.let { data -> handleBannerResult(data) }
                }
            }
            resultAllFeed.observe(this@MainActivity) {
                runOnUiThread {
                    handleMovieeResult(it)
                }
            }
            loadingState.observe(this@MainActivity) {
                handleLoadingState(it)
            }
            errorState.observe(this@MainActivity) {
                handleErrorState(it)
            }
        }
    }


    private fun handleInfoState(msg: String) {
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

        binding.iWrapperBanner.apply {
            if (data.isNotEmpty()) {
                tvGenreBanner.makeVisible()
                mbMoreInfoBanner.makeVisible()
            }
            ivBanner.loadCoilImageHQ(banner.posterPath)
            tvGenreBanner.text = DataMapper.mappingMovieGenreListFromId(banner.genreId)
                        .joinToString(" • ") { it.name.toString() }

            mbMoreInfoBanner.setOnClickListener {
                startActivity(Intent(this@MainActivity, DetailActivity::class.java).apply {
                    putExtra(DetailActivity.MOVIEE, banner.id) })
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
                    getAllFeed()
                }
            }
        }
    }

    private fun changeBackgroundColorAppBar(color: Int, fraction: Double): Int {
        val red: Int = Color.red(color)
        val green: Int = Color.green(color)
        val blue: Int = Color.blue(color)
        val alpha: Int = (Color.alpha(color) * fraction).toInt()
        return Color.argb(alpha, red, green, blue)
    }

}
