package com.msusman.matrix.ui.login

import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import com.msusman.matrix.Presenter
import com.msusman.matrix.architecture.AsyncData
import com.msusman.matrix.domain.model.AuthenticationException
import com.msusman.matrix.domain.model.UserId
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class LoginPresenter : Presenter<LoginState> {

    @Composable
    override fun present(): LoginState {
        val coroutineScope = rememberCoroutineScope()
        val loginFormState = remember { mutableStateOf(LoginFormState.Default) }
        val loginResultState: MutableState<AsyncData<UserId>> = remember {
            mutableStateOf(AsyncData.Uninitialized)
        }

        fun handleEvents(event: LoginEvent) {
            when (event) {
                LoginEvent.Reset -> {
                    loginResultState.value = AsyncData.Uninitialized
                }

                LoginEvent.Submit -> {
                    coroutineScope.submit(loginFormState.value, loginResultState)
                }

                is LoginEvent.setUsername -> updateFormState(loginFormState) {
                    copy(username = event.userName)
                }

                is LoginEvent.setPassword -> updateFormState(loginFormState) {
                    copy(password = event.password)
                }
            }

        }
        return LoginState(
            accountProvider = defaultAccountProvider,
            formState = loginFormState.value,
            loginResultState = loginResultState.value,
            eventSink = ::handleEvents
        )
    }

    private fun updateFormState(
        loginFormState: MutableState<LoginFormState>,
        updateLambda: LoginFormState.() -> LoginFormState
    ) {
        loginFormState.value = updateLambda(loginFormState.value)

    }

    private fun CoroutineScope.submit(
        loginFormState: LoginFormState,
        loginResultState: MutableState<AsyncData<UserId>>
    ) = launch {
        loginResultState.value = AsyncData.Loading()
        delay(2000)
        if (loginFormState.username == loginFormState.password) {
            loginResultState.value = AsyncData.Success(UserId("2345"))
        } else {
            loginResultState.value =
                AsyncData.Failure(AuthenticationException.Generic(message = "M_FORBIDDEN"))
        }
    }
}