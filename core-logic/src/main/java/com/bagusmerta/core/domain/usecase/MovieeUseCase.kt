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
package com.bagusmerta.core.domain.usecase

import com.bagusmerta.core.data.Resource
import com.bagusmerta.core.domain.model.Cast
import com.bagusmerta.core.domain.model.Moviee
import com.bagusmerta.core.domain.model.MovieeDetail
import com.bagusmerta.core.domain.model.MovieeFavorite
import com.bagusmerta.core.domain.model.MovieeSearch
import io.reactivex.Maybe
import io.reactivex.Single

interface MovieeUseCase {
    fun getAllMovies(): Single<Resource<List<Moviee>>>
    fun getUpcomingMovies(): Single<Resource<List<Moviee>>>
    fun getPopularMovies(): Single<Resource<List<Moviee>>>
    fun getNowPlayingMovies(): Single<Resource<List<Moviee>>>
    fun getTopRatedMovies(): Single<Resource<List<Moviee>>>
    fun getDetailMovies(movieId: Int): Single<Resource<MovieeDetail>>
    fun getMovieCast(movieId: Int): Single<Resource<List<Cast>>>
    fun checkFavoriteMovies(id: Int): Maybe<Moviee>
    fun getAllFavoriteMovies(isFavorite: Boolean): Single<Resource<List<MovieeFavorite>>>
    fun setFavoriteMovies(data: Moviee, isFavorite: Boolean, genre: String): Single<Unit>
    fun deleteFavoriteMovies(id: Int): Single<Resource<String>>
    fun searchMovies(query: String): Single<Resource<List<MovieeSearch>>>
    fun getSimilarMovies(movieId: Int): Single<Resource<List<Moviee>>>
    fun getTrendingMovies(): Single<Resource<List<Moviee>>>

}
