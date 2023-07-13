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
package com.bagusmerta.moviee.presentation.main

import android.app.Application
import android.content.Context
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.HomeFeed
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.moviee.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class MainViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()
    private val _resultBanner = MutableLiveData<List<Moviee>?>()
    private val _resultAllFeed = MutableLiveData<List<HomeFeed>?>()
    private val _listFeed = mutableListOf<HomeFeed>()

    private val _loadingState = MutableLiveData<Boolean>()
    private val _errorState = MutableLiveData<String>()
    private val _emptyState = MutableLiveData<Boolean>()

    init {
        getAllFeed()
    }

    val resultBanner: LiveData<List<Moviee>?>
        get() = _resultBanner

    val resultAllFeed: LiveData<List<HomeFeed>?>
        get() = _resultAllFeed

    val loadingState: LiveData<Boolean>
        get() = _loadingState

    val emptyState: LiveData<Boolean>
        get() = _emptyState

    val errorState: LiveData<String>
        get() = _errorState

    fun getAllFeed(){
        Observable.zip(
            useCase.getAllMovies().toObservable(),
            useCase.getUpcomingMovies().toObservable(),
            useCase.getPopularMovies().toObservable(),
            useCase.getTopRatedMovies().toObservable(),
            useCase.getNowPlayingMovies().toObservable(),
        ) { newlyData, upcomingData, popularData, topRatedData, nowPlayingData ->

            getBannerMovies()
            validateNewlyMovies(newlyData)
            validateUpcoming(upcomingData)
            validatePopular(popularData)
            validateTopRated(topRatedData)
            validateNowPLaying(nowPlayingData)

        }
            .doOnSubscribe { _loadingState.postValue(true) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe ({
                _loadingState.postValue(false)
                _resultAllFeed.postValue(_listFeed)
            },{ error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    private fun validateNewlyMovies(newly: Resource<List<Moviee>>){
        when(newly){
            is Resource.Success -> {
                val homeFeed = HomeFeed( NEWLY_HEADER,
                    NEWLY_SUB, newly.data, 1)

                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(newly.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }

    private fun validateUpcoming(upcoming: Resource<List<Moviee>>){
        when(upcoming){
            is Resource.Success -> {
                val homeFeed = HomeFeed(
                    UPCOMING_HEADER,
                    UPCOMING_SUB, upcoming.data, 2)

                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(upcoming.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }

    private fun validatePopular(popular: Resource<List<Moviee>>){
        when(popular){
            is Resource.Success -> {
                val homeFeed = HomeFeed(
                    POPULAR_HEADER,
                    POPULAR_SUB, popular.data, 3)
                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(popular.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }

    private fun validateTopRated(top: Resource<List<Moviee>>){
        when(top){
            is Resource.Success -> {
                val homeFeed = HomeFeed(
                    TOP_HEADER,
                    TOP_SUB, top.data, 4)

                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(top.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }

    private fun validateNowPLaying(now: Resource<List<Moviee>>){
        when(now){
            is Resource.Success -> {
                val homeFeed = HomeFeed(
                    NOW_HEADER,
                    NOW_SUB, now.data, 5)

                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(now.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }


    private fun getBannerMovies(){
        useCase.getAllMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> _resultBanner.postValue(value.data)
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

    /**
     * Temporary workaround for dynamic string in each movie feeds,
     * as for the old ones might leak the context
     */
    companion object {
        const val NEWLY_HEADER = "Newly Movies"
        const val UPCOMING_HEADER = "Upcoming Movies"
        const val POPULAR_HEADER = "Popular Movies"
        const val TOP_HEADER = "Top Rated Movies"
        const val NOW_HEADER = "Now Playing Movies"

        const val NEWLY_SUB = "Fresh and anticipated movies"
        const val UPCOMING_SUB = "Discover the latest blockbuster"
        const val POPULAR_SUB = "The ultimate movies experience"
        const val TOP_SUB = "Cinematic masterpiece lauded"
        const val NOW_SUB = "Thrilling options available"
    }

}
