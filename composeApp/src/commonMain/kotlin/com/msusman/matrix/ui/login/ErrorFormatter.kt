package com.msusman.matrix.ui.login

import androidx.compose.runtime.Composable
import com.msusman.matrix.domain.model.AuthErrorCode
import com.msusman.matrix.domain.model.AuthenticationException
import com.msusman.matrix.domain.model.errorCode
import matrixclientkmp.composeapp.generated.resources.Res
import matrixclientkmp.composeapp.generated.resources.error_unknown
import matrixclientkmp.composeapp.generated.resources.screen_login_error_deactivated_account
import matrixclientkmp.composeapp.generated.resources.screen_login_error_invalid_credentials
import org.jetbrains.compose.resources.StringResource

@Composable
fun loginError(exception: Throwable): StringResource {
    val authException =
        exception as? AuthenticationException ?: return Res.string.error_unknown
    val str = when (authException.errorCode) {
        AuthErrorCode.FORBIDDEN -> Res.string.screen_login_error_invalid_credentials
        AuthErrorCode.USER_DEACTIVATED -> Res.string.screen_login_error_deactivated_account
        AuthErrorCode.UNKNOWN -> Res.string.error_unknown
    }
    return str
}