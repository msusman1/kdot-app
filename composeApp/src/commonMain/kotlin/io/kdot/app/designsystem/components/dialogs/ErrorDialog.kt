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
import androidx.compose.ui.window.DialogProperties
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.theme.components.SimpleAlertDialogContent
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ErrorDialog(
    content: String,
    onSubmit: () -> Unit,
    modifier: Modifier = Modifier,
    title: String? = ErrorDialogDefaults.title,
    submitText: String = ErrorDialogDefaults.submitText,
    onDismiss: () -> Unit = onSubmit,
    canDismiss: Boolean = true,
) {
    BasicAlertDialog(
        modifier = modifier,
        onDismissRequest = onDismiss,
        properties = DialogProperties(dismissOnClickOutside = canDismiss, dismissOnBackPress = canDismiss)
    ) {
        ErrorDialogContent(
            title = title,
            content = content,
            submitText = submitText,
            onSubmitClick = onSubmit,
        )
    }
}

@Composable
private fun ErrorDialogContent(
    content: String,
    onSubmitClick: () -> Unit,
    title: String? = ErrorDialogDefaults.title,
    submitText: String = ErrorDialogDefaults.submitText,
) {
    SimpleAlertDialogContent(
        title = title,
        content = content,
        submitText = submitText,
        onSubmitClick = onSubmitClick,
    )
}

object ErrorDialogDefaults {
    val title: String @Composable get() = stringResource(Resources.String.dialog_title_error)
    val submitText: String @Composable get() = stringResource(Resources.String.action_ok)
}
