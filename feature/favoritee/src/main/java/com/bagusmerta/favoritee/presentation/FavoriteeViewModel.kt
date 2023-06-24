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
package com.bagusmerta.feature.favoritee.presentation

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.core.domain.usecase.MovieeUseCase
import io.reactivex.disposables.CompositeDisposable
import timber.log.Timber

class FavoriteeViewModel(private val useCase: MovieeUseCase): ViewModel() {

    private val mCompositeDisposable = CompositeDisposable()
    private val _loadingState = MutableLiveData<Boolean>()
    private val _emptyState = MutableLiveData<Boolean>()
    private val _errorMsg = MutableLiveData<String>()
    private val _result = MutableLiveData<List<MovieeFavorite>?>()

    val emptyState: LiveData<Boolean>
        get() = _emptyState

    val result: LiveData<List<MovieeFavorite>?>
        get() = _result

    val loadingState: LiveData<Boolean>
        get() = _loadingState

    val errorState: LiveData<String>
        get() = _errorMsg

    fun getFavoriteMovies(isFavorite: Boolean) {
        useCase.getAllFavoriteMovies(isFavorite)
            .doOnSubscribe { _loadingState.postValue(true) }
            .doAfterTerminate {
                _loadingState.postValue(false)
                mCompositeDisposable.clear()
            }
            .subscribe({ value ->
                when(value){
                    is Resource.Success -> {
                        _emptyState.postValue(false)
                        _result.postValue(value.data)
                    }
                    is Resource.Empty -> _emptyState.postValue(true)
                    is Resource.Error -> _errorMsg.postValue(value.errorMessage)
                }
            }, { err ->
                Timber.e(err.message.toString())
            }).let(mCompositeDisposable::add)
    }

    override fun onCleared() {
        super.onCleared()
        mCompositeDisposable.clear()
    }

}
