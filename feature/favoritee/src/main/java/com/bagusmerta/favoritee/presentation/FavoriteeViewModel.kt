package com.bagusmerta.feature.favoritee.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class FavoriteeViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()
    private val _loadingState = MutableLiveData<Boolean>()
    private val _emptyState = MutableLiveData<Boolean>()
    private val _errorMsg = MutableLiveData<String>()
    private val _result = MutableLiveData<List<MovieeFavorite>?>()

    val emptyState: LiveData<Boolean>
        get() = _emptyState

    val result: LiveData<List<MovieeFavorite>?>
        get() = _result

    val loadingState: LiveData<Boolean>
        get() = _loadingState

    val errorState: LiveData<String>
        get() = _errorMsg

    fun getFavoriteMovies(isFavorite: Boolean) {
        useCase.getAllFavoriteMovies(isFavorite)
            .doOnSubscribe { _loadingState.postValue(true) }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> {
                        _loadingState.postValue(false)
                        _emptyState.postValue(false)
                        _result.postValue(value.data)
                    }
                    is Resource.Empty -> {
                        _loadingState.postValue(false)
                        _emptyState.postValue(true)
                    }
                    is Resource.Error -> { _errorMsg.postValue(value.errorMessage) }
                }
            }, { err ->
                Timber.e(err.message.toString())
            }).let(mCompositeDisposable::add)
    }


    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }

}