package com.bagusmerta.moviee.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber


class DetailViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val _btnState = MutableLiveData<Boolean?>()
    private val _result = MutableLiveData<MovieeDetail?>()
    private val mCompositeDisposable = CompositeDisposable()
    private val _castResult = MutableLiveData<List<Cast>?>()
    private val _similarMovieResult  = MutableLiveData<List<Moviee>?>()

    private val _loadingState = MutableLiveData<Boolean>()
    private val _errorState = MutableLiveData<String>()
    private val _emptyState = MutableLiveData<Boolean>()

    val btnState: LiveData<Boolean?>
        get() = _btnState

    val result: LiveData<MovieeDetail?>
        get() = _result

    val castResult: LiveData<List<Cast>?>
        get() = _castResult

    val similarMovieResult: LiveData<List<Moviee>?>
        get() = _similarMovieResult

    val loadingState: LiveData<Boolean>
        get() = _loadingState

    fun getSimilarMovie(movieId: Int){
        useCase.getSimilarMovie(movieId)
            .doOnSubscribe{
                _loadingState.postValue(true)
            }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> _similarMovieResult.postValue(value.data)
                    is Resource.Error -> _errorState.postValue(value.errorMessage)
                    is Resource.Empty -> _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message)
            }).let(mCompositeDisposable::add)
    }

    fun getDetailMovies(movieId: Int){
        useCase.getDetailMovies(movieId)
            .doOnSubscribe{
                _loadingState.postValue(true)
            }
            .subscribe({ value ->
                when(value) {
                    is Resource.Success -> {
                        _result.postValue(value.data)
                        _loadingState.postValue(false)
                    }
                    is Resource.Error -> _errorState.postValue(value.errorMessage)
                    is Resource.Empty -> _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message)
            }).let(mCompositeDisposable::add)
    }

    fun getMovieCast(movieId: Int){
        useCase.getMovieCast(movieId)
            .doOnSubscribe {
                _loadingState.postValue(true)
            }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> { _castResult.postValue(value.data)}
                    is Resource.Error -> _errorState.postValue(value.errorMessage)
                    is Resource.Empty -> _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message)
            }).let(mCompositeDisposable::add)
    }

//    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean){
//        useCase.setFavoriteMovies(data, isFavorite)
//            .subscribe({
//                _btnState.postValue(isFavorite)
//            }, { error ->
//                Timber.e(error.message.toString())
//            }).let(mCompositeDisposable::add)
//    }
//
//    fun checkFavoriteMovies(id: Int){
//        useCase.checkFavoriteMovies(id)
//            .subscribe({ data ->
//                _btnState.postValue(data.isFavorite)
//                _result.postValue(data)
//            }, { error ->
//                Timber.e(error.message.toString())
//            }).let(mCompositeDisposable::add)
//    }

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()

    }
}
