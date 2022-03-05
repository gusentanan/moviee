package com.bagusmerta.core.data


sealed class Resource<out R> {
    class Success<out T>(val data: T) : Resource<T>()
    class Error(val errorMessage: String) : Resource<Nothing>()
    object Empty : Resource<Nothing>()
}

