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
    private val _favoriteeState: MutableLiveData<FavoriteMovieeState> = MutableLiveData()

    val favoriteState: LiveData<FavoriteMovieeState>
        get() = _favoriteeState

    fun getAllFavoritesMovies(){
        useCase.getAllFavoriteMovies(true)
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({ data ->
                if(data.isEmpty()){
                    _favoriteeState.postValue(FavoriteMovieeState.EmptyState)
                }else{
                    _favoriteeState.postValue(FavoriteMovieeState.GetAllFavoriteMovies(data))
                }
            }, { err ->
                Timber.e(err.message.toString())
            }).let(mCompositeDisposable::add)
    }
}

sealed class FavoriteMovieeState{
    object EmptyState: FavoriteMovieeState()
    data class GetAllFavoriteMovies(val data: List<Moviee>): FavoriteMovieeState()
}