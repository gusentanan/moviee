package com.bagusmerta.moviee.di


import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.domain.usecase.MovieeUseCaseImpl
import com.bagusmerta.feature.allmovie.presentation.AllMovieViewModel
import com.bagusmerta.feature.detail.presentation.DetailViewModel
import com.bagusmerta.feature.favoritee.presentation.FavoriteeViewModel
import com.bagusmerta.feature.search.presentation.SearchViewModel
import com.bagusmerta.moviee.presentation.main.MainViewModel
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieeUseCase> { MovieeUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get(), androidApplication()) } // app context to access Resource or perform operation that require context
    viewModel { DetailViewModel(get()) }
    viewModel { FavoriteeViewModel(get()) }
    viewModel { SearchViewModel(get()) }
    viewModel { AllMovieViewModel(get()) }
}
