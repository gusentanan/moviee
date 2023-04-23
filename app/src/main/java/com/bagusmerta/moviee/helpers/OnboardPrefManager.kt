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