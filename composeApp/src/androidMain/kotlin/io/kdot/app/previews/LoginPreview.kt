package io.kdot.app.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kdot.app.architecture.AsyncData
import io.kdot.app.ui.login.LoginFormState
import io.kdot.app.ui.login.LoginState
import io.kdot.app.ui.login.defaultAccountProvider
import io.kdot.app.ui.login.LoginView

@Composable
@Preview
fun LoginPreview() {
    LoginView(
        state = LoginState(
            formState = LoginFormState("", ""),
            accountProvider = defaultAccountProvider,
            loginResultState = AsyncData.Uninitialized,
            eventSink = { s -> }
        ),
        onBackClick = {}
    )
}