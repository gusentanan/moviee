package com.bagusmerta.moviee.presentation.search

import android.app.SearchManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.utils.Constants
import com.bagusmerta.moviee.R
import com.bagusmerta.moviee.databinding.ActivitySearchBinding
import com.bagusmerta.moviee.utils.makeGone
import com.bagusmerta.moviee.utils.makeVisible
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.subjects.PublishSubject
import org.koin.androidx.viewmodel.ext.android.viewModel
import java.util.concurrent.TimeUnit

class SearchActivity : AppCompatActivity() {

    private val binding: ActivitySearchBinding by lazy { ActivitySearchBinding.inflate(layoutInflater) }
    private val searchAdapter: SearchAdapter by lazy { SearchAdapter(this) }
    private val searchViewModel: SearchViewModel by viewModel()
    private val items = mutableListOf<Moviee>()
    private val mCompositeDisposable = CompositeDisposable()

    private val searchTextChange = PublishSubject.create<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        initView()

        initStateObserver()
        initRecyclerView()
        initSearchMenu()
    }

    private fun initView() {
        window.statusBarColor = ContextCompat.getColor(this, R.color.colorSecondaryDark)

        findViewById<ImageView>(R.id.btn_favorite).setOnClickListener {
            val uriFavorite = Uri.parse(Constants.URI_FAVORITE)
            startActivity(Intent(Intent.ACTION_VIEW, uriFavorite))
        }
        binding.lottieEmptyRes.apply {
            lottieView.setAnimation("lottie/empty_state_lottie.json")
            lottieView.playAnimation()
        }
    }

    private fun initSearchMenu(){
        val searchManager = getSystemService(Context.SEARCH_SERVICE) as SearchManager
        val searchView = findViewById<SearchView>(R.id.sv_search)

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
            loadingState.observe(this@SearchActivity){
                handleLoadingState(it)
            }
            result.observe(this@SearchActivity){
                it?.let { handleResult(it) }
            }
            emptyState.observe(this@SearchActivity){
                handleEmptyResult(it)
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

    private fun handleEmptyResult(state: Boolean){
        binding.lottieEmptyRes.root.apply {
            if(state) makeVisible() else makeGone()
        }
        searchAdapter.clearItems()
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