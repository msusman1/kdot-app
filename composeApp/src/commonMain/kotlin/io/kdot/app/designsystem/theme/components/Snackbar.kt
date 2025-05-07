/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.theme.components

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.SnackbarDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp
import io.kdot.app.designsystem.components.buttons.ButtonVisuals
import io.kdot.app.ui.theme.appColors

@Composable
fun Snackbar(
    message: String,
    modifier: Modifier = Modifier,
    action: ButtonVisuals? = null,
    dismissAction: ButtonVisuals? = null,
    actionOnNewLine: Boolean = false,
    shape: Shape = RoundedCornerShape(8.dp),
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    actionContentColor: Color = MaterialTheme.appColors.snackBarLabelColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
) {
    Snackbar(
        modifier = modifier,
        action = action?.let { @Composable { it.Composable() } },
        dismissAction = dismissAction?.let { @Composable { it.Composable() } },
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor,
        content = { Text(text = message) },
    )
}

@Composable
fun Snackbar(
    modifier: Modifier = Modifier,
    action: @Composable (() -> Unit)? = null,
    dismissAction: @Composable (() -> Unit)? = null,
    actionOnNewLine: Boolean = false,
    shape: Shape = RoundedCornerShape(8.dp),
    containerColor: Color = SnackbarDefaults.color,
    contentColor: Color = MaterialTheme.colorScheme.inverseOnSurface,
    actionContentColor: Color = MaterialTheme.appColors.snackBarLabelColor,
    dismissActionContentColor: Color = SnackbarDefaults.dismissActionContentColor,
    content: @Composable () -> Unit
) {
    androidx.compose.material3.Snackbar(
        modifier = modifier,
        action = action,
        dismissAction = dismissAction,
        actionOnNewLine = actionOnNewLine,
        shape = shape,
        containerColor = containerColor,
        contentColor = contentColor,
        actionContentColor = actionContentColor,
        dismissActionContentColor = dismissActionContentColor,
        content = content,
    )
}