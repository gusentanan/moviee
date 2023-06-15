/*
 * Designed and developed by 2023 gusentanan (Bagus Merta)
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0

 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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
