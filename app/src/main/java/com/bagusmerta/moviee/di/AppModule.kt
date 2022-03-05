package com.bagusmerta.moviee.di


import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.core.domain.usecase.MovieeUseCaseImpl
import com.bagusmerta.moviee.presentation.detail.DetailViewModel
import com.bagusmerta.moviee.presentation.main.MainViewModel
import com.bagusmerta.moviee.presentation.search.SearchViewModel
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module


val useCaseModule = module {
    factory<MovieeUseCase> { MovieeUseCaseImpl(get()) }
}

val viewModelModule = module {
    viewModel { MainViewModel(get()) }
    viewModel { DetailViewModel(get()) }
    viewModel { SearchViewModel(get()) }
}

/* TO DO */
// 1. no image response on poster and backdrop (search, detail) // DONE
// 2. No Data lottie animation on search feature // DONE
// 3. search feature with implementation of rx java  as asynchronous request // DONE
// 4. favorite button state on main page didn't apply livedata behaviour, so that the state (as a view)
//    didn't update when we click the button
// 5. finishing touch, apply style for each text, dimens for ui, color. make the code looks better
// 6. unit testing, ui testing
// 7. new activity animation (from main -> search)