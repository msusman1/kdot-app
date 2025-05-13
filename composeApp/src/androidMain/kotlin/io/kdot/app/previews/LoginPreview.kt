package io.kdot.app.previews

import androidx.compose.runtime.Composable
import io.kdot.app.architecture.AsyncData
import io.kdot.app.ui.login.LoginFormState
import io.kdot.app.ui.login.LoginState
import io.kdot.app.ui.login.LoginView

@Composable
@PreviewDayNight
fun LoginPreview() {
    KDotPreview {
        LoginView(
            state = LoginState(
                formState = LoginFormState.Default,
                loginResultState = AsyncData.Uninitialized
            ),
            onBackClick = {},
            handleAction = {}
        )
    }
}