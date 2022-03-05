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
import com.bagusmerta.core.utils.singleTransformerIo
import io.reactivex.disposables.CompositeDisposable


class DetailViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val _btnState = MutableLiveData<Boolean>()
    private val mCompositeDisposable = CompositeDisposable()
    val btnState: LiveData<Boolean>
        get() = _btnState

    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean){
        useCase.setFavoriteMovies(data, isFavorite)
            .doAfterTerminate { mCompositeDisposable.clear() }
            .subscribe({
                _btnState.postValue(isFavorite)
            }, { error ->
                Log.e("DetailViewModel: ", error.message.toString())
            }).let(mCompositeDisposable::add)
    }
}
