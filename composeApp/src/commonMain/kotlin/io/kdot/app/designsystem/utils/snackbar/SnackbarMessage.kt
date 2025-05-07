/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.utils.snackbar

import androidx.compose.material3.SnackbarDuration
import org.jetbrains.compose.resources.StringResource
import kotlin.concurrent.atomics.AtomicBoolean
import kotlin.concurrent.atomics.ExperimentalAtomicApi
import kotlin.random.Random

/**
 * A message to be displayed in a [Snackbar].
 * @param messageResId The message to be displayed.
 * @param duration The duration of the message. The default value is [SnackbarDuration.Short].
 * @param actionResId The action text to be displayed. The default value is `null`.
 * @param isDisplayed Used to track if the current message is already displayed or not.
 * @param id The unique identifier of the message. The default value is a random long.
 * @param action The action to be performed when the action is clicked.
 */
data class SnackbarMessage @OptIn(ExperimentalAtomicApi::class) constructor(
    val messageResId: StringResource,
    val duration: SnackbarDuration = SnackbarDuration.Short,
    val actionResId: StringResource? = null,
    val isDisplayed: AtomicBoolean = AtomicBoolean(false),
    val id: Long = Random.nextLong(),
    val action: () -> Unit = {},
)
