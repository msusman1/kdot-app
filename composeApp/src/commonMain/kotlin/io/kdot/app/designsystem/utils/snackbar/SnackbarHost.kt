/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.utils.snackbar

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.SnackbarHostState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kdot.app.designsystem.components.buttons.ButtonVisuals
import io.kdot.app.designsystem.theme.components.Snackbar
import io.kdot.app.ui.roomlist.IconSource

@Composable
fun SnackbarHost(hostState: SnackbarHostState, modifier: Modifier = Modifier) {
    androidx.compose.material3.SnackbarHost(hostState, modifier) { data ->
        Snackbar(
            // Add default padding
            modifier = Modifier.padding(12.dp),
            message = data.visuals.message,
            action = data.visuals.actionLabel?.let { ButtonVisuals.Text(it, data::performAction) },
            dismissAction = if (data.visuals.withDismissAction) {
                ButtonVisuals.Icon(
                    iconSource = IconSource.Vector(Icons.Default.Close),
                    action = data::dismiss
                )
            } else {
                null
            },
        )
    }
}
