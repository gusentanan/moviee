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
package com.bagusmerta.moviee.helpers

import android.content.Context
import android.content.SharedPreferences

class OnboardPrefManager(context: Context) {

    private val pref: SharedPreferences
    private val editor: SharedPreferences.Editor

    var isFirstTime: Boolean
    get(){
        return pref.getBoolean(IS_FIRST_TIME, true)
    }
    set(value) {
        editor.putBoolean(IS_FIRST_TIME, value)
        editor.commit()
    }

    init {
        pref = context.getSharedPreferences(PREF_NAME, Context.MODE_PRIVATE)
        editor = pref.edit()
    }

    companion object {
        private const val IS_FIRST_TIME = "is_first_time"
        private const val PREF_NAME = "pref_name"
    }

}
