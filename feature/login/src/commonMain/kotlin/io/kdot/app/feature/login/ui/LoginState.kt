package io.kdot.app.feature.login.ui

import io.kdot.app.libraries.architecture.AsyncData
import io.kdot.app.libraries.architecture.model.UserId


data class LoginState(
    val formState: LoginFormState,
    val loginResultState: AsyncData<UserId>,
) {
    companion object {
        val Default = LoginState(
            formState = LoginFormState.Default,
            loginResultState = AsyncData.Uninitialized
        )
    }

    val submitEnabled: Boolean
        get() = loginResultState !is AsyncData.Failure &&
                formState.username.isNotEmpty() &&
                formState.password.isNotEmpty()
}

data class LoginFormState(val username: String, val password: String) {
    companion object {
        val Default = LoginFormState("", "")
    }
}


