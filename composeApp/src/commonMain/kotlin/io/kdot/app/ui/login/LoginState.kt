package io.kdot.app.ui.login

import io.kdot.app.architecture.AsyncData
import io.kdot.app.domain.model.UserId

data class LoginState(
    val formState: LoginFormState,
    val accountProvider: AccountProvider,
    val loginResultState: AsyncData<UserId>,
    val eventSink: (event: LoginEvent) -> Unit,
) {
    val submitEnabled: Boolean
        get() = loginResultState !is AsyncData.Failure &&
                formState.username.isNotEmpty() &&
                formState.password.isNotEmpty()
}

data class LoginFormState(val username: String, val password: String){
    companion object {
        val Default = LoginFormState("", "")
    }
}

data class AccountProvider(
    val url: String,
    val title: String = url.removePrefix("https://").removePrefix("http://"),
    val subtitle: String? = null,
    val isPublic: Boolean = false,
    val isMatrixOrg: Boolean = false,
    val isValid: Boolean = false,
)

val defaultAccountProvider = AccountProvider(
    url = "https://matrix.org",
    subtitle = null,
    isPublic = true,
    isMatrixOrg = true,
)
