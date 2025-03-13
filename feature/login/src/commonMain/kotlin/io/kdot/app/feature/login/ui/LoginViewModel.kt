package io.kdot.app.feature.login.ui

import io.kdot.app.libraries.architecture.AsyncData
import io.kdot.app.libraries.architecture.model.AuthenticationException
import io.kdot.app.libraries.architecture.model.UserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class LoginViewModel {
    private val _loginState: MutableStateFlow<LoginState> =
        MutableStateFlow(LoginState.Default)
    val loginState: StateFlow<LoginState> = _loginState.asStateFlow()
    private val coroutineScope = CoroutineScope(Job())

    fun handleAction(event: LoginEvent) {
        when (event) {
            LoginEvent.Reset -> {
                _loginState.value = LoginState.Default
            }

            LoginEvent.Submit -> {
                coroutineScope.submit(loginState.value.formState)
            }

            is LoginEvent.setUsername -> updateFormState(loginState.value.formState) {
                copy(username = event.userName)
            }

            is LoginEvent.setPassword -> updateFormState(loginState.value.formState) {
                copy(password = event.password)
            }
        }

    }

    private fun updateFormState(
        loginFormState: LoginFormState,
        updateLambda: LoginFormState.() -> LoginFormState
    ) {
        _loginState.value = loginState.value.copy(formState = updateLambda(loginFormState))
    }

    private fun CoroutineScope.submit(
        loginFormState: LoginFormState,
    ) = launch {
        _loginState.value = loginState.value.copy(loginResultState = AsyncData.Loading())
        delay(2000)
        if (loginFormState.username == loginFormState.password) {
            _loginState.value =
                loginState.value.copy(loginResultState = AsyncData.Success(UserId("2345")))
        } else {
            _loginState.value = loginState.value.copy(
                loginResultState = AsyncData.Failure(
                    AuthenticationException.Generic(message = "M_FORBIDDEN")
                )
            )
        }
    }
}

