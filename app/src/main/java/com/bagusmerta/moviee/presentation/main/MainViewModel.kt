package com.bagusmerta.moviee.presentation.main

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.HomeFeed
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import com.bagusmerta.moviee.R
import io.reactivex.Observable
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import timber.log.Timber


class MainViewModel(private val useCase: MovieeUseCase, application: Application): AndroidViewModel(application) {

    private val mCompositeDisposable = CompositeDisposable()
    private val _resultBanner = MutableLiveData<List<Moviee>?>()
    private val _resultAllFeed = MutableLiveData<List<HomeFeed>?>()
    private val _listFeed = mutableListOf<HomeFeed>()

    private val _loadingState = MutableLiveData<Boolean>()
    private val _errorState = MutableLiveData<String>()
    private val _emptyState = MutableLiveData<Boolean>()

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

    private fun getMyStringResource(resId: Int): String {
        return getApplication<Application>().getString(resId)
    }
    fun getAllFeed(){
        Observable.zip(
            useCase.getAllMovies().toObservable(),
            useCase.getUpcomingMovies().toObservable(),
            useCase.getPopularMovies().toObservable(),
            useCase.getTopRatedMovies().toObservable(),
            useCase.getNowPlayingMovies().toObservable(),
        ) { newlyData, upcomingData, popularData, topRatedData, nowPlayingData ->

            validateNewlyMovies(newlyData)
            validateUpcoming(upcomingData)
            validatePopular(popularData)
            validateTopRated(topRatedData)
            validateNowPLaying(nowPlayingData)

        }
            .doOnSubscribe { _loadingState.postValue(true) }
            .doOnComplete { _loadingState.postValue(false) }
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .subscribe ({
                _resultAllFeed.postValue(_listFeed)
            },{ error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    private fun validateNewlyMovies(newly: Resource<List<Moviee>>){
        when(newly){
            is Resource.Success -> {
                val homeFeed = HomeFeed(getMyStringResource(R.string.tv_newly_movies),
                    getMyStringResource(R.string.tv2_recommend_movies), newly.data, 1)

                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(newly.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }

    private fun validateUpcoming(upcoming: Resource<List<Moviee>>){
        when(upcoming){
            is Resource.Success -> {
                val homeFeed = HomeFeed(getMyStringResource(R.string.tv_upcoming_movies),
                    getMyStringResource(R.string.tv2_upcoming_movies), upcoming.data, 2)

                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(upcoming.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }

    private fun validatePopular(popular: Resource<List<Moviee>>){
        when(popular){
            is Resource.Success -> {
                val homeFeed = HomeFeed(getMyStringResource(R.string.tv_popular_movies),
                    getMyStringResource(R.string.tv2_popular_movies), popular.data, 3)
                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(popular.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }

    private fun validateTopRated(top: Resource<List<Moviee>>){
        when(top){
            is Resource.Success -> {
                val homeFeed = HomeFeed(getMyStringResource(R.string.tv_toprated_movies),
                    getMyStringResource(R.string.tv2_toprated_movies), top.data, 4)

                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(top.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }

    private fun validateNowPLaying(now: Resource<List<Moviee>>){
        when(now){
            is Resource.Success -> {
                val homeFeed = HomeFeed(getMyStringResource(R.string.tv_nowplaying_movies),
                    getMyStringResource(R.string.tv2_nowplaying_movies), now.data, 5)

                _listFeed.add(homeFeed)
            }
            is Resource.Error ->  _errorState.postValue(now.errorMessage)
            is Resource.Empty ->  _emptyState.postValue(true)
        }
    }


    fun getBannerMovies(){
        useCase.getAllMovies()
            .doOnSubscribe {
                _loadingState.postValue(true)
                _emptyState.postValue(false)
            }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> {
                        _resultBanner.postValue(value.data)
                    }
                    is Resource.Error ->  _errorState.postValue(value.errorMessage)
                    is Resource.Empty ->  _emptyState.postValue(true)
                }
            }, { error ->
                Timber.e(error.message.toString())
            }).let(mCompositeDisposable::add)
    }

    override fun onCleared() {
        mCompositeDisposable.clear()
        super.onCleared()
    }
}