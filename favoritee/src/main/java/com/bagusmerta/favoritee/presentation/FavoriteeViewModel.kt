package com.bagusmerta.favoritee.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class FavoriteeViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()
    private val _emptyState = MutableLiveData<Boolean>()
    private val _result = MutableLiveData<List<Moviee>?>()

    val emptyState: LiveData<Boolean>
        get() = _emptyState

    val result: LiveData<List<Moviee>?>
        get() = _result

    fun getFavoriteMovies(isFavorite: Boolean) {
        useCase.getAllFavoriteMovies(isFavorite)
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ data ->
                if (data.isEmpty()) {
                    _emptyState.postValue(true)
                } else {
                    _emptyState.postValue(false)
                    _result.postValue(data)
                }
            }, { err ->
                Timber.e(err.message.toString())
            }).let(mCompositeDisposable::add)
    }

}