package io.kdot.app.domain.model

sealed class AuthenticationException(message: String) : Exception(message) {
    class InvalidServerName(message: String) : AuthenticationException(message)
    class SlidingSyncVersion(message: String) : AuthenticationException(message)
    class Generic(message: String) : AuthenticationException(message)
}
