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
package com.bagusmerta.core.utils

import com.bagusmerta.core.BuildConfig

object Constants {
    const val API_KEY = BuildConfig.API_KEY
    const val BASE_URL = BuildConfig.BASE_API
    const val GET_ALL_MOVIES = "SELECT * FROM moviee_table"
    const val DELETE_FAVORITE_MOVIES = "DELETE FROM moviee_table WHERE id = :movieId"
    const val GET_ALL_FAVORITE_MOVIES = "SELECT * FROM moviee_table WHERE is_favorite = :isFavorite"
    const val CHECK_FAVORITE_MOVIES = "SELECT * FROM moviee_table WHERE id = :movieId"
}
