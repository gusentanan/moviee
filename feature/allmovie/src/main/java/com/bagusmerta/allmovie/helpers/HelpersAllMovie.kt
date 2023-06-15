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
package com.bagusmerta.feature.allmovie.helpers

object HelpersAllMovie {
    fun findMovieSection(key: Int): String? {
        movieSectionMap.let {
            return it[key]
        }
    }
    private val movieSectionMap: HashMap<Int, String> = hashMapOf(
        1 to "Newly Movies",
        2 to "Upcoming Movies",
        3 to "Popular Movies",
        4 to "Top Rated Movies",
        5 to "Now Playing Movies"
    )
}
