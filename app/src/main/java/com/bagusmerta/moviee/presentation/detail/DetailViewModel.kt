package com.bagusmerta.moviee.presentation.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase


class DetailViewModel(private val useCase: MovieeUseCase): ViewModel() {

    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean){
        useCase.setFavoriteMovies(data, isFavorite)
    }

    fun getDetailMovies(id: Int): LiveData<Resource<Moviee>> {
        return LiveDataReactiveStreams.fromPublisher(useCase.getDetailMoviesData(id))
    }

}