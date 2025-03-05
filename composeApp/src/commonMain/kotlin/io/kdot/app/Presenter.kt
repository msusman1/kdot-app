package io.kdot.app

import androidx.compose.runtime.Composable

fun interface Presenter<State> {
    @Composable
    fun present(): State
}