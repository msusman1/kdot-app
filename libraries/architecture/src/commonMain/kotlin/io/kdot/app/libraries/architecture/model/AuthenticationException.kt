package io.kdot.app.libraries.architecture.model

sealed class AuthenticationException(message: String) : Exception(message) {
    class InvalidServerName(message: String) : AuthenticationException(message)
    class SlidingSyncVersion(message: String) : AuthenticationException(message)
    class Generic(message: String) : AuthenticationException(message)
}
