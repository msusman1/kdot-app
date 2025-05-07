/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.text

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.unit.Dp

/**
 * Return the maximum value between the receiver value and the value with fontScale applied.
 * So if fontScale is >= 1f, the same value is returned, and if fontScale is < 1f, so returned value
 * will be smaller.
 */
@Composable
fun Dp.applyScaleDown(): Dp = with(LocalDensity.current) {
    return this@applyScaleDown * fontScale.coerceAtMost(1f)
}

/**
 * Return the minimum value between the receiver value and the value with fontScale applied.
 * So if fontScale is <= 1f, the same value is returned, and if fontScale is > 1f, so returned value
 * will be bigger.
 */
@Composable
fun Dp.applyScaleUp(): Dp = with(LocalDensity.current) {
    return this@applyScaleUp * fontScale.coerceAtLeast(1f)
}

