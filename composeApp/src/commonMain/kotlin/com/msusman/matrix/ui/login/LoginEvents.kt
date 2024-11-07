package com.msusman.matrix.ui.login

sealed interface LoginEvent {
    data class setUsername(val userName: String) : LoginEvent
    data class setPassword(val password: String) : LoginEvent
    data object Submit : LoginEvent
    data object Reset : LoginEvent
}