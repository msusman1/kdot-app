package io.kdot.app.ui.login

import io.kdot.app.architecture.AsyncData
import net.folivo.trixnity.core.model.UserId


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


