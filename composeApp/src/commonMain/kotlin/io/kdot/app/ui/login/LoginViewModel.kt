package io.kdot.app.ui.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kdot.app.architecture.AsyncData
import io.kdot.app.architecture.model.AuthenticationException
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch/*
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.loginWithPassword
import net.folivo.trixnity.client.media.InMemoryMediaStore
import net.folivo.trixnity.client.media.MediaServiceImpl
import net.folivo.trixnity.client.serverDiscovery
import net.folivo.trixnity.clientserverapi.model.authentication.IdentifierType*/
import org.koin.dsl.module


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

   /*     server.serverDiscovery().onSuccess { url ->
            val matrixClient = MatrixClient.loginWithPassword(
                baseUrl = url,
                identifier = IdentifierType.User(loginFormState.username),
                password = loginFormState.password,
                deviceId = null,
                initialDeviceDisplayName = null,
                repositoriesModule = module { },
                mediaStore = InMemoryMediaStore()
            )
        }.onFailure {
            _loginState.value = loginState.value.copy(
                loginResultState = AsyncData.Failure(
                    AuthenticationException.Generic(message = it.message ?: "Unknown Error")
                )
            )
        }
*/

    }
}

