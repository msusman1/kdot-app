/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.core.data

import kotlinx.coroutines.flow.Flow

inline fun <A> tryOrNull(onError: ((Throwable) -> Unit) = { }, operation: () -> A): A? {
    return try {
        operation()
    } catch (any: Throwable) {
        onError.invoke(any)
        null
    }
}


inline fun <T1, T2, T3, T4, T5, T6,T7, R> combine(
    flow: Flow<T1>,
    flow2: Flow<T2>,
    flow3: Flow<T3>,
    flow4: Flow<T4>,
    flow5: Flow<T5>,
    flow6: Flow<T6>,
    flow7: Flow<T7>,
    crossinline transform: suspend (T1, T2, T3, T4, T5, T6,T7) -> R
): Flow<R> {
    return kotlinx.coroutines.flow.combine(
        flow,
        flow2,
        flow3,
        flow4,
        flow5,
        flow6,
        flow7
    ) { args: Array<*> ->
        @Suppress("UNCHECKED_CAST")
        transform(
            args[0] as T1,
            args[1] as T2,
            args[2] as T3,
            args[3] as T4,
            args[4] as T5,
            args[5] as T6,
            args[6] as T7,
        )
    }
}

fun <T> MutableList<T>.swap(index1: Int, index2: Int) {
    if (index1 in indices && index2 in indices) {
        val temp = this[index1]
        this[index1] = this[index2]
        this[index2] = temp
    }
}