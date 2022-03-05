package com.bagusmerta.moviee.presentation.search

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.utils.singleTransformerComputation
import io.reactivex.disposables.CompositeDisposable

class SearchViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val _loadingState = MutableLiveData<Boolean>()
    private val _result = MutableLiveData<List<Moviee>?>()
    private val _errorState = MutableLiveData<String?>()
    private val mCompositeDisposable = CompositeDisposable()

    val loadingState: LiveData<Boolean>
        get() = _loadingState

    val result: LiveData<List<Moviee>?>
        get() = _result

    val errorState: LiveData<String?>
        get() = _errorState

    fun searchMovies(query: String){
        useCase.searchMovies(query)
            .doOnSubscribe { _loadingState.postValue(true) }
            .doAfterTerminate{ _loadingState.postValue(false) }
            .subscribe({ movies ->
                when(movies){
                    is Resource.Success -> _result.postValue(movies.data)
                    is Resource.Error -> _errorState.postValue(movies.message)
                    is Resource.Loading -> _loadingState.postValue(true)
                }
            }, { error ->
                Log.e("SearchViewModel: ", error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    override fun onCleared() {
        mCompositeDisposable.dispose()
    }
}