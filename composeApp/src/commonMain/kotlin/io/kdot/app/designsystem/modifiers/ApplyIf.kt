/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.modifiers

import androidx.compose.ui.Modifier

/**
 * Applies the [ifTrue] modifier when the [condition] is true, [ifFalse] otherwise.
 */
fun Modifier.applyIf(
    condition: Boolean,
    ifTrue: Modifier.() -> Modifier,
    ifFalse: (Modifier.() -> Modifier)? = null
): Modifier = this then when {
    condition -> ifTrue(Modifier)
    ifFalse != null -> ifFalse(Modifier)
    else -> Modifier
}
