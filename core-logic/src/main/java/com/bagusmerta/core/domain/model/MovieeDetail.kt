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
package com.bagusmerta.core.domain.model

data class MovieeDetail(
    val id: Int?,
    val title: String?,
    val overview: String?,
    val releaseDate: String?,
    val backdropPath: String?,
    val posterPath: String?,
    val isFavorite: Boolean?,
    val rating: Double?,
    val genres: List<Int>?,
    val runtime: Int?,
    val keyVideo: String?,
    val titleVideo: String?,
    val budget: Double?,
    val revenue: Double?,
    var tagline: String?,
    var voteCount: Int?,
    var originalLanguage: String?,
    var originalTitle: String?,
    var status: String?,
    var productionCountries: String?,
    var productionCompanies: List<String?>?
)
