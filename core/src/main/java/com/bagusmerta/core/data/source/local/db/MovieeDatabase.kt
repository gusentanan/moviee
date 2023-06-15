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
package com.bagusmerta.core.data.source.local.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.bagusmerta.core.data.source.local.dao.MovieeDao
import com.bagusmerta.core.data.source.local.entity.MovieeEntity

@Database(entities = [MovieeEntity::class], version = 1, exportSchema = false)
abstract class MovieeDatabase: RoomDatabase() {
    abstract fun movieeDao(): MovieeDao
}
