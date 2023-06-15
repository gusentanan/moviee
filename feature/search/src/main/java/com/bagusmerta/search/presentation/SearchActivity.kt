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
package com.bagusmerta.feature.search.presentation

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.MovieeSearch
import com.bagusmerta.core.utils.Constants
import com.bagusmerta.feature.search.R
import com.bagusmerta.feature.search.databinding.ActivitySearchBinding
import com.bagusmerta.utility.makeGone
import com.bagusmerta.utility.makeVisible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(this) }
    private val searchViewModel: SearchViewModel by viewModel()
    private val items = mutableListOf<MovieeSearch>()
    private val mCompositeDisposable = CompositeDisposable()

    private val searchTextChange = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()
        handlingBackButton()

        initStateObserver()
        initRecyclerView()
        initSearchMenu()
    }

    private fun initView() {
        binding.apply {
            btnFavorite.setOnClickListener{
                val uriFavorite = Uri.parse(Constants.URI_FAVORITE)
                startActivity(Intent(Intent.ACTION_VIEW, uriFavorite))
            }
            lottieEmptyRes.apply {
                lottieView.setAnimation("lottie/empty_state_lottie.json")
                lottieView.playAnimation()
            }
        }
    }

    private fun initSearchMenu(){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = binding.svSearch

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                searchTextChange.onComplete()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                searchTextChange.onNext(query)
                searchTextChange
                    .doOnComplete { mCompositeDisposable.clear() }
                    .debounce(400, TimeUnit.MILLISECONDS)
                    .filter{ it.isNotEmpty() && it.length >= 3 }
                    .map { it.lowercase().trim() }
                    .subscribeOn(AndroidSchedulers.mainThread())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{
                        searchViewModel.searchMovies(it)
                    }.let { mCompositeDisposable::add }

                return true
            }
        })
    }

    private fun initStateObserver(){
        with(searchViewModel){
            getTrendingMovies()
            loadingState.observe(this@SearchActivity){
                handleLoadingState(it)
            }
            result.observe(this@SearchActivity){
                it?.let { handleSearchResult(it) }
            }
            emptyState.observe(this@SearchActivity){
                handleEmptyResult(it)
            }
            errorState.observe(this@SearchActivity){
                handleErrorState(it)
            }
        }
    }

    private fun initRecyclerView(){
        binding.apply {
            rvSearchMovies.layoutManager = LinearLayoutManager(this@SearchActivity)
            rvSearchMovies.setHasFixedSize(true)
            rvSearchMovies.adapter = searchAdapter
        }
    }

    private fun handlingBackButton(){
        binding.btnBack.setOnClickListener {
                onBackPressed()
            }
    }

    private fun handleEmptyResult(state: Boolean){
        binding.apply {
            lottieEmptyRes.root.let {
                if(state) it.makeVisible() else it.makeGone()
            }
            lottieEmptyRes.tvEmptyState.text = getString(R.string.search_movie_not_found)
        }
        searchAdapter.clearItems()
    }

    private fun handleSearchResult(data: List<MovieeSearch>){
        items.clear()
        items.addAll(data)
        searchAdapter.setItems(items)
    }

    private fun handleErrorState(msg: String) {
        Timber.tag("ERROR").e(msg)
        binding.apply {
            errorState.root.makeVisible()
            errorState.btnTryAgain.setOnClickListener {
                initStateObserver()
            }
        }
    }

    private fun handleLoadingState(state:Boolean){
        binding.apply {
            errorState.root.makeGone()
            progressBar.apply {
                if(state){
                    wrapperTopSearches.makeGone()
                    makeVisible()
                } else{
                    tvTopSearches.text = context.getString(R.string.tv_search_results)
                    wrapperTopSearches.makeVisible()
                    makeGone()
                }
            }
        }
    }

}
