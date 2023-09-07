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
package com.bagusmerta.core.data.source.remote.movieeResponse

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MovieCastResponse(
    @Json(name = "cast")
    var movieCast: List<CastResponse>
)

@JsonClass(generateAdapter = true)
data class CastResponse(

    @Json(name = "character")
    var character: String?,

    @Json(name = "name")
    var name: String?,

    @Json(name = "profile_path")
    var profilePicPath: String?
)
