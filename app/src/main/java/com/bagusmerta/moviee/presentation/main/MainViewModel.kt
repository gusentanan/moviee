package com.bagusmerta.moviee.presentation.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class MainViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()
    private val _result = MutableLiveData<List<Moviee>?>()
    private val _loadingState = MutableLiveData<Boolean>()
    private val _errorState = MutableLiveData<String>()
    private val _emptyState = MutableLiveData<Boolean>()

    val result: LiveData<List<Moviee>?>
        get() = _result

    val loadingState: LiveData<Boolean>
        get() = _loadingState

    val emptyState: LiveData<Boolean>
        get() = _emptyState

    val errorState: LiveData<String>
        get() = _errorState

    fun getAllMovies(){
        useCase.getAllMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> {
                        _result.postValue(value.data)
                        _loadingState.postValue(false)
                    }
                    is Resource.Error ->  _errorState.postValue(value.errorMessage)
                    is Resource.Empty ->  _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }
}