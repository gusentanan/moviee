package com.bagusmerta.moviee.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class DetailViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val _btnState = MutableLiveData<Boolean?>()
    private val _result = MutableLiveData<Moviee>()
    private val mCompositeDisposable = CompositeDisposable()

    val btnState: LiveData<Boolean?>
        get() = _btnState

    val result: LiveData<Moviee>
        get() = _result

    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean){
        useCase.setFavoriteMovies(data, isFavorite)
            .subscribe({
                _btnState.postValue(isFavorite)
            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    fun checkFavoriteMovies(id: Int){
        useCase.checkFavoriteMovies(id)
            .subscribe({ data ->
                _btnState.postValue(data.isFavorite)
                _result.postValue(data)
            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }
}
