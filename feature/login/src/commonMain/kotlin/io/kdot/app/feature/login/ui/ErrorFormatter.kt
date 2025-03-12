/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.feature.login.ui

import io.kdot.app.libraries.architecture.model.AuthErrorCode
import io.kdot.app.libraries.architecture.model.AuthenticationException
import io.kdot.app.libraries.architecture.model.errorCode
import io.kdot.app.libraries.designsystem.Resources
import org.jetbrains.compose.resources.StringResource


fun loginError(
    throwable: Throwable
): StringResource {
    val authException =
        throwable as? AuthenticationException ?: return Resources.String.error_unknown
    return when (authException.errorCode) {
        AuthErrorCode.FORBIDDEN -> Resources.String.screen_login_error_invalid_credentials
        AuthErrorCode.USER_DEACTIVATED -> Resources.String.screen_login_error_deactivated_account
        AuthErrorCode.UNKNOWN -> Resources.String.error_unknown
    }
}
