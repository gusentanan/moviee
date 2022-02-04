package com.bagusmerta.moviee.presentation.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.LiveDataReactiveStreams
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase


class MainViewModel(private val useCase: MovieeUseCase): ViewModel() {

    val movieeList = LiveDataReactiveStreams.fromPublisher(useCase.getAllMovies())
}