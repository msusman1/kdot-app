package io.kdot.app.ui.login

import androidx.compose.runtime.Composable
import io.kdot.app.domain.model.AuthErrorCode
import io.kdot.app.domain.model.AuthenticationException
import io.kdot.app.domain.model.errorCode
import kdotapp.composeapp.generated.resources.Res
import kdotapp.composeapp.generated.resources.error_unknown
import kdotapp.composeapp.generated.resources.screen_login_error_deactivated_account
import kdotapp.composeapp.generated.resources.screen_login_error_invalid_credentials
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