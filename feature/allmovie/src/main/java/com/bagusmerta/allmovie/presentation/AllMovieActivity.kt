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
package com.bagusmerta.feature.allmovie.presentation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.feature.allmovie.databinding.ActivityAllMovieBinding
import com.bagusmerta.utility.MovieeHomeFeed
import com.bagusmerta.utility.initStatusBar
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
        initStatusBar()

        initBtnBack()
        initStateObserver()
        initRecycleView()
    }

    private fun initBtnBack() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            onBackInvokedDispatcher.registerOnBackInvokedCallback(1000) {
                onBackPressedDispatcher.onBackPressed() }
        }
        binding.btnBackAll.setOnClickListener {
            onBackPressedDispatcher.onBackPressed()
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
             MovieeHomeFeed.NEWLY_MOVIES.id -> {
                handleTitlePage(MovieeHomeFeed.NEWLY_MOVIES.text)
                allMovieViewModel.getAllMovies()
            }
            MovieeHomeFeed.UPCOMING_MOVIES.id -> {
                handleTitlePage(MovieeHomeFeed.UPCOMING_MOVIES.text)
                allMovieViewModel.getUpcomingMovies()
            }
            MovieeHomeFeed.POPULAR_MOVIES.id -> {
                handleTitlePage(MovieeHomeFeed.POPULAR_MOVIES.text)
                allMovieViewModel.getPopularMovies()
            }
            MovieeHomeFeed.TOP_RATED_MOVIES.id -> {
                handleTitlePage(MovieeHomeFeed.TOP_RATED_MOVIES.text)
                allMovieViewModel.getTopRatedMovies()
            }
            MovieeHomeFeed.NOW_PLAYING_MOVIES.id -> {
                handleTitlePage(MovieeHomeFeed.NOW_PLAYING_MOVIES.text)
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
