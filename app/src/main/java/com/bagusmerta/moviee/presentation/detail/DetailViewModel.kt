package com.bagusmerta.moviee.presentation.detail

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.utils.singleTransformerComputation
import io.reactivex.disposables.CompositeDisposable


class DetailViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val _btnState = MutableLiveData<Boolean>()

    val btnState: LiveData<Boolean>
        get() = _btnState

    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean){
        useCase.setFavoriteMovies(data, isFavorite)
            .compose(singleTransformerComputation())
            .subscribe({
                _btnState.postValue(isFavorite)
            }, { error ->
                Log.e("DetailViewModel", error.message.toString())
            }).let(CompositeDisposable()::add)

    }

    fun getDetailMovies(id: Int): LiveData<Resource<Moviee>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.getDetailMoviesData(id))
    }


}