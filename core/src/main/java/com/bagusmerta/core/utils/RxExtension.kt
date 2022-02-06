package com.bagusmerta.core.utils

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun <T> flowableTransformerComputation(): FlowableTransformer<T, T>{
    return FlowableTransformer {
        it.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
    }
}

fun <T> flowableTransformerIo(): FlowableTransformer<T, T>{
    return FlowableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .take(1)
    }
}

fun completableTransformerIo(): CompletableTransformer{
    return CompletableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> singleTransformerComputation(): SingleTransformer<T, T>{
    return SingleTransformer {
        it.subscribeOn(Schedulers.computation())
            .observeOn(AndroidSchedulers.mainThread())
    }
}