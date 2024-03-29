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
package com.bagusmerta.feature.allmovie.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class AllMovieViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()
    private val _result = MutableLiveData<List<Moviee>?>()
    private val _loadingState = MutableLiveData<Boolean>()
    private val _errorState = MutableLiveData<String>()
    private val _emptyState = MutableLiveData<Boolean>()

    val result: LiveData<List<Moviee>?>
        get() = _result

    val loadingState: LiveData<Boolean>
        get() = _loadingState

    val emptyState: LiveData<Boolean>
        get() = _emptyState

    val errorState: LiveData<String>
        get() = _errorState

    fun getAllMovies(){
        useCase.getAllMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .doAfterTerminate { _loadingState.postValue(false) }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> _result.postValue(value.data)
                    is Resource.Error ->  _errorState.postValue(value.errorMessage)
                    is Resource.Empty ->  _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    fun getUpcomingMovies(){
        useCase.getUpcomingMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .doAfterTerminate { _loadingState.postValue(false) }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> _result.postValue(value.data)
                    is Resource.Error ->  _errorState.postValue(value.errorMessage)
                    is Resource.Empty ->  _emptyState.postValue(true)
                }

            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    fun getPopularMovies(){
        useCase.getPopularMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .doAfterTerminate { _loadingState.postValue(false) }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> _result.postValue(value.data)
                    is Resource.Error ->  _errorState.postValue(value.errorMessage)
                    is Resource.Empty ->  _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    fun getTopRatedMovies(){
        useCase.getTopRatedMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .doAfterTerminate { _loadingState.postValue(false) }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> _result.postValue(value.data)
                    is Resource.Error ->  _errorState.postValue(value.errorMessage)
                    is Resource.Empty ->  _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    fun getNowPlayingMovies(){
        useCase.getNowPlayingMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .doAfterTerminate { _loadingState.postValue(false) }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> _result.postValue(value.data)
                    is Resource.Error ->  _errorState.postValue(value.errorMessage)
                    is Resource.Empty ->  _emptyState.postValue(true)
                }

            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

}
