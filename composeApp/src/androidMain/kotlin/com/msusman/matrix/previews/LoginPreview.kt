package com.msusman.matrix.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import com.msusman.matrix.architecture.AsyncData
import com.msusman.matrix.ui.login.LoginFormState
import com.msusman.matrix.ui.login.LoginState
import com.msusman.matrix.ui.login.LoginView
import com.msusman.matrix.ui.login.defaultAccountProvider

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