/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.components.dialogs

import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.theme.components.SimpleAlertDialogContent
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ConfirmationDialog(
    content: String,
    onSubmitClick: () -> Unit,
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = null,
    submitText: String = stringResource(Resources.String.action_ok),
    cancelText: String = stringResource( Resources.String.action_cancel),
    destructiveSubmit: Boolean = false,
    thirdButtonText: String? = null,
    onCancelClick: () -> Unit = onDismiss,
    onThirdButtonClick: () -> Unit = {},
    icon: @Composable (() -> Unit)? = null,
) {
    BasicAlertDialog(modifier = modifier, onDismissRequest = onDismiss) {
        ConfirmationDialogContent(
            title = title,
            content = content,
            submitText = submitText,
            cancelText = cancelText,
            thirdButtonText = thirdButtonText,
            destructiveSubmit = destructiveSubmit,
            onSubmitClick = onSubmitClick,
            onCancelClick = onCancelClick,
            onThirdButtonClick = onThirdButtonClick,
            icon = icon,
        )
    }
}

@Composable
private fun ConfirmationDialogContent(
    content: String,
    submitText: String,
    cancelText: String,
    onSubmitClick: () -> Unit,
    onCancelClick: () -> Unit,
    title: String? = null,
    thirdButtonText: String? = null,
    onThirdButtonClick: () -> Unit = {},
    destructiveSubmit: Boolean = false,
    icon: @Composable (() -> Unit)? = null,
) {
    SimpleAlertDialogContent(
        title = title,
        content = content,
        submitText = submitText,
        onSubmitClick = onSubmitClick,
        cancelText = cancelText,
        onCancelClick = onCancelClick,
        thirdButtonText = thirdButtonText,
        onThirdButtonClick = onThirdButtonClick,
        destructiveSubmit = destructiveSubmit,
        icon = icon,
    )
}


