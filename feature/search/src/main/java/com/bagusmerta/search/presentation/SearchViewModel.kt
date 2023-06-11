package com.bagusmerta.feature.search.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.MovieeSearch
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class SearchViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    private val _result = MutableLiveData<List<MovieeSearch>?>()
    private val _errorState = MutableLiveData<String>()
    private val _emptyState = MutableLiveData<Boolean>()
    private val mCompositeDisposable = CompositeDisposable()

    val loadingState: LiveData<Boolean>
        get() = _loadingState

    val result: LiveData<List<MovieeSearch>?>
        get() = _result

    val errorState: LiveData<String>
        get() = _errorState

    val emptyState: LiveData<Boolean>
        get() = _emptyState

    fun getTrendingMovies(){
        useCase.getTrendingMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .doAfterTerminate { _loadingState.postValue(false) }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> {
                        _result.postValue(value.data.map {
                                MovieeSearch(
                                    id = it.id,
                                    title = it.title,
                                    backdropPath = it.backdropPath,
                                    releaseDate = it.releaseDate,
                                    rating = it.rating,
                                    genreId = it.genreId,
                                    isFavorite = it.isFavorite
                                )
                            }
                        )
                    }
                    is Resource.Error ->  _errorState.postValue(value.errorMessage)
                    is Resource.Empty ->  _emptyState.postValue(true)
                }

            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    fun searchMovies(query: String){
        useCase.searchMovies(query)
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .doAfterTerminate{ _loadingState.postValue(false) }
            .subscribe({ movies ->
                when(movies){
                    is Resource.Success -> _result.postValue(movies.data)
                    is Resource.Error -> _errorState.postValue(movies.errorMessage)
                    is Resource.Empty -> _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

}