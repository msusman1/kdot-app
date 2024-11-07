package com.msusman.matrix

import androidx.compose.runtime.Composable

fun interface Presenter<State> {
    @Composable
    fun present(): State
}