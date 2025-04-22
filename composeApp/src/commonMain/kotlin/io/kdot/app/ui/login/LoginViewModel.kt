package io.kdot.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kdot.app.architecture.AsyncData
import io.kdot.app.architecture.model.AuthenticationException
import io.kdot.app.matrix.MatrixClientFactory
import io.kdot.app.matrix.MatrixClientProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(
    private val matrixClientFactory: MatrixClientFactory,
    private val matrixClientProvider: MatrixClientProvider
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginState.Default)
    val loginState = _loginState.asStateFlow()


    fun handleAction(event: LoginEvent) {
        when (event) {
            LoginEvent.Reset -> {
                _loginState.value = LoginState.Default
            }

            LoginEvent.Submit -> {
                viewModelScope.submit(loginState.value.formState)
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
        matrixClientFactory.createFromLogin(loginFormState.username, loginFormState.password)
            .onSuccess {
                matrixClientProvider.setClient(it)
                val userId = it.userId
                _loginState.value = loginState.value.copy(
                    loginResultState = AsyncData.Success(userId)
                )
            }.onFailure {
                _loginState.value = loginState.value.copy(
                    loginResultState = AsyncData.Failure(
                        AuthenticationException.Generic(message = it.message ?: "Unknown Error")
                    )
                )
            }


    }
}
