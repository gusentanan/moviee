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
package com.bagusmerta.utility

import io.reactivex.CompletableTransformer
import io.reactivex.FlowableTransformer
import io.reactivex.MaybeTransformer
import io.reactivex.SingleTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


fun <T> flowableTransformerIo(): FlowableTransformer<T, T>{
    return FlowableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
            .take(1)
    }
}

fun completableTransformerIo(): CompletableTransformer{
    return CompletableTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}

fun <T> singleTransformerIo(): SingleTransformer<T, T>{
    return SingleTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(Schedulers.single())
    }
}

fun <T> maybeTransformerIo(): MaybeTransformer<T, T>{
    return MaybeTransformer {
        it.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
    }
}