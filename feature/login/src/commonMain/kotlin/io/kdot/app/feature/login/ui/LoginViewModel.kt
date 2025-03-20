package io.kdot.app.feature.login.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kdot.app.libraries.architecture.AsyncData
import io.kdot.app.libraries.architecture.model.AuthenticationException
import io.kdot.app.libraries.architecture.model.UserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import net.folivo.trixnity.client.serverDiscovery


class LoginViewModel : ViewModel() {
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
        val server = "https://matrix.org"
        val url = server.serverDiscovery()
       /* MatrixClient.loginWithPassword(
            baseUrl = url,
            identifier = IdentifierType.User(loginFormState.username),
            password = loginFormState.password,
            deviceId = null,
            initialDeviceDisplayName = null,
            repositoriesModulesFactory = {
                Module
            },
            mediaStoreFactory = {

            }
        )*/
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

