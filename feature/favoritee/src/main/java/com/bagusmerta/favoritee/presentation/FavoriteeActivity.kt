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
package com.bagusmerta.feature.favoritee.presentation

import android.os.Build
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.feature.favoritee.R
import com.bagusmerta.feature.favoritee.databinding.ActivityFavoriteeBinding
import com.bagusmerta.utility.extensions.initStatusBar
import com.bagusmerta.utility.extensions.makeErrorToast
import com.bagusmerta.utility.extensions.makeGone
import com.bagusmerta.utility.extensions.makeInfoToast
import com.bagusmerta.utility.extensions.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber


class FavoriteeActivity : AppCompatActivity() {

    private val binding: ActivityFavoriteeBinding by lazy {
        ActivityFavoriteeBinding.inflate(
            layoutInflater
        )
    }
    private val favoriteeViewModel: FavoriteeViewModel by viewModel()
    private val favoriteeAdapter: FavoriteeAdapter by lazy { FavoriteeAdapter(this) }
    private val items = mutableListOf<MovieeFavorite>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        supportActionBar?.setTitle(R.string.favorite_page_title)

        initStatusBar()
        handleBackPressed()
        initClearButton()
        initObserverState()
        initRecyclerView()
    }

   private fun handleBackPressed(){
       binding.btnBack.setOnClickListener {
               onBackPressedDispatcher.onBackPressed()
       }
   }

    private fun initClearButton(){
        binding.btnClearFavorite.setOnClickListener {
            this.makeInfoToast("This feature is currently unavailable")
        }
    }

    private fun initRecyclerView() {
        binding.apply {
            rvFavMovie.layoutManager = LinearLayoutManager(this@FavoriteeActivity)
            rvFavMovie.adapter = favoriteeAdapter
        }
    }

    private fun initObserverState() {
        favoriteeViewModel.apply {

            loadingState.observe(this@FavoriteeActivity){
                handleLoadingState(it)
            }
            result.observe(this@FavoriteeActivity) {
                it?.let { handleFavoriteMovieResult(it) }
            }
            emptyState.observe(this@FavoriteeActivity) {
                handleEmptyStateResult(it)
            }
            errorState.observe(this@FavoriteeActivity){
                handleErrorState(it)
            }
        }
    }

    private fun handleErrorState(msg: String?) {
        this.makeErrorToast("Oops Something Wrong Happen")
        Timber.e(msg)
    }

    private fun handleLoadingState(state: Boolean) {
        binding.cvProgressBar.root.apply {
            if(state) makeVisible() else makeGone()
        }
    }

    private fun handleFavoriteMovieResult(data: List<MovieeFavorite>) {
        items.clear()
        items.addAll(data)
        favoriteeAdapter.setFavoriteItem(items)
    }

    private fun handleEmptyStateResult(state: Boolean) {
        binding.emptyState.root.apply {
            if (state) {
                favoriteeAdapter.clearItems()
                makeVisible()
            } else makeGone()
        }
    }

    override fun onResume() {
        favoriteeViewModel.getFavoriteMovies(true)
        super.onResume()
    }

}
