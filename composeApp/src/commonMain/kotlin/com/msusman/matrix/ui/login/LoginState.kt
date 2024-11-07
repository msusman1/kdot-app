package com.msusman.matrix.ui.login

import com.msusman.matrix.architecture.AsyncData
import com.msusman.matrix.domain.model.UserId

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

data class LoginFormState(val username: String, val password: String)

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
