package com.bagusmerta.moviee.presentation.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.Constants
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivitySearchBinding
import com.bagusmerta.moviee.utils.makeGone
import com.bagusmerta.moviee.utils.makeVisible
import org.koin.androidx.viewmodel.ext.android.viewModel

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(this) }
    private val searchViewModel: SearchViewModel by viewModel()
    private val items = mutableListOf<Moviee>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initAppBar()

        initStateObserver()
        initRecyclerView()
        initSearchMenu()
    }

    private fun initAppBar() {
        findViewById<ImageView>(R.id.btn_favorite).setOnClickListener {
            val uriFavorite = Uri.parse(Constants.URI_FAVORITE)
            startActivity(Intent(Intent.ACTION_VIEW, uriFavorite))
        }
    }

    private fun initSearchMenu(){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = findViewById<SearchView>(R.id.sv_search)

        searchView.setSearchableInfo(searchManager.getSearchableInfo(componentName))
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String): Boolean {
                if (query.isNotEmpty()){
                    items.clear()
                    searchViewModel.searchMovies(query)
                    searchView.clearFocus()

                }else{
                    searchView.clearFocus()
                }
                return true
            }

            override fun onQueryTextChange(query: String?): Boolean = false

        })
    }

    private fun initStateObserver(){
        with(searchViewModel){
            loadingState.observe(this@SearchActivity){
                handleLoadingState(it)
            }
            result.observe(this@SearchActivity){
                it?.let { handleResult(it) }
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

    private fun handleResult(data: List<Moviee>){
        items.clear()
        items.addAll(data)
        searchAdapter.setItems(items)
    }

    private fun handleLoadingState(state:Boolean){
        binding.progressBar.apply {
            if(state) makeVisible() else makeGone()
        }
    }
}