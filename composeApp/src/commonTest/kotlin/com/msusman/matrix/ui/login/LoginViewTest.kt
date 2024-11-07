package com.msusman.matrix.ui.login

import androidx.compose.ui.test.ExperimentalTestApi
import androidx.compose.ui.test.runComposeUiTest
import com.msusman.matrix.architecture.AsyncData
import kotlin.test.Test

class LoginViewTest {

    @OptIn(ExperimentalTestApi::class)
    @Test
    fun verifyTitleDisplayAccountProviderTitle() = runComposeUiTest {
        val accountProvider = AccountProvider(
            url = "https://matrix.org"
        )
        val loginState = LoginState(
            formState = LoginFormState(username = "", password = ""),
            accountProvider = accountProvider,
            loginResultState = AsyncData.Uninitialized,
            eventSink = { ev -> }
        )
        setContent {
            LoginView(loginState, onBackClick = {})
        }

    }
}