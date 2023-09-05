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
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.MovieeSearch
import com.bagusmerta.feature.search.R
import com.bagusmerta.feature.search.databinding.ActivitySearchBinding
import com.bagusmerta.search.presentation.SearchGridAdapter
import com.bagusmerta.utility.datasource.RecyclerViewEnum
import com.bagusmerta.utility.extensions.initStatusBar
import com.bagusmerta.utility.extensions.makeGone
import com.bagusmerta.utility.extensions.makeVisible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import timber.log.Timber
import java.util.concurrent.TimeUnit


class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val searchListAdapter: SearchListAdapter by lazy { SearchListAdapter(this) }
    private val searchGridAdapter: SearchGridAdapter by lazy { SearchGridAdapter(this) }
    private val searchViewModel: SearchViewModel by viewModel()
    private val items = mutableListOf<MovieeSearch>()
    private val mCompositeDisposable = CompositeDisposable()

    private val searchTextChange = PublishSubject.create<String>()
    private var viewFlag: RecyclerViewEnum = RecyclerViewEnum.LIST_VIEW

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initStatusBar()

        initView()
        handlingBackPressed()
        initStateObserver()
        initRecyclerView()
        toggleRecylerViewChanges()
        initSearchMenu()
    }

    private fun initView() {
        binding.apply {
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

        binding.cvSearch.setOnClickListener {
            searchView.isIconified = false
            searchView.requestFocus()

            val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.showSoftInput(searchView, InputMethodManager.SHOW_IMPLICIT)
        }

        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                searchTextChange.onComplete()
                return true
            }

            override fun onQueryTextChange(query: String): Boolean {
                searchTextChange.onNext(query)
                searchTextChange
                    .doAfterTerminate{ mCompositeDisposable.clear() }
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
            when(viewFlag){
                RecyclerViewEnum.GRID_VIEW -> {
                    rvSearchMovies.layoutManager = GridLayoutManager(this@SearchActivity, 3, GridLayoutManager.VERTICAL, false)
                    rvSearchMovies.setHasFixedSize(true)
                    searchGridAdapter.setItems(items)
                    rvSearchMovies.adapter = searchGridAdapter
                }
                RecyclerViewEnum.LIST_VIEW -> {
                    rvSearchMovies.layoutManager = LinearLayoutManager(this@SearchActivity)
                    rvSearchMovies.setHasFixedSize(true)
                    searchListAdapter.setItems(items)
                    rvSearchMovies.adapter = searchListAdapter
                }
            }

        }
    }

    private fun handlingBackPressed(){
        binding.btnBack.setOnClickListener {
                onBackPressedDispatcher.onBackPressed()
        }
    }

    private fun toggleRecylerViewChanges(){
        binding.btnView.setOnClickListener {
            if(viewFlag == RecyclerViewEnum.LIST_VIEW){
                viewFlag = RecyclerViewEnum.GRID_VIEW
                binding.btnView.setImageResource(com.bagusmerta.common_ui.R.drawable.ic_baseline_view_module_24)
                initRecyclerView()
            } else {
                viewFlag = RecyclerViewEnum.LIST_VIEW
                binding.btnView.setImageResource(com.bagusmerta.common_ui.R.drawable.ic_baseline_view_list_24)
                initRecyclerView()
            }
        }
    }

    private fun handleEmptyResult(state: Boolean){
        binding.apply {
            lottieEmptyRes.root.let {
                if(state) it.makeVisible() else it.makeGone()
            }
        }
        searchListAdapter.clearItems()
    }

    private fun handleSearchResult(data: List<MovieeSearch>){
        items.clear()
        items.addAll(data)
        if(viewFlag == RecyclerViewEnum.LIST_VIEW){
            searchListAdapter.setItems(items)
        } else {
            searchGridAdapter.setItems(items)
        }

    }

    private fun handleErrorState(msg: String) {
        Timber.tag("ERROR").e(msg)
        binding.apply {
            errorState.root.makeVisible()
            errorState.root.setOnClickListener {
                initStateObserver()
            }
        }
    }

    private fun handleLoadingState(state:Boolean){
        binding.apply {
            errorState.root.makeGone()
            cvProgressBar.root.apply {
                if(state) makeVisible() else makeGone()
            }
        }
    }

}
