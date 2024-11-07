package com.msusman.matrix.ui.login

import androidx.compose.runtime.Composable
import com.msusman.matrix.Presenter
import com.msusman.matrix.architecture.AsyncData

class LoginPresenter : Presenter<LoginState> {


    fun handleEvents(events: LoginEvent) {

    }

    @Composable
    override fun present(): LoginState {
        return LoginState(
            accountProvider = defaultAccountProvider,
            formState = LoginFormState("", ""),
            loginResultState = AsyncData.Uninitialized,
            eventSink = ::handleEvents
        )
    }
}