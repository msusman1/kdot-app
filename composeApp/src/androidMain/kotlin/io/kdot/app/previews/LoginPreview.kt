package io.kdot.app.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kdot.app.feature.login.ui.LoginFormState
import io.kdot.app.feature.login.ui.LoginState
import io.kdot.app.feature.login.ui.LoginView
import io.kdot.app.libraries.architecture.AsyncData

@Composable
@Preview
fun LoginPreview() {
    LoginView(
        state = LoginState(
            formState = LoginFormState.Default,
            loginResultState = AsyncData.Uninitialized
        ),
        onBackClick = {},
        handleAction = {}
    )
}