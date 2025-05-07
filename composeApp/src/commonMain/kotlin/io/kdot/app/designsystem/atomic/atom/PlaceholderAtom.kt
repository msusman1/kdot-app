/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.atomic.atom

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import io.kdot.app.ui.theme.appColors
import io.kdot.app.ui.theme.placeholderBackground

@Composable
fun PlaceholderAtom(
    width: Dp,
    height: Dp,
    modifier: Modifier = Modifier,
    color: Color = MaterialTheme.appColors.placeholderBackground,
) {
    Box(
        modifier = modifier
            .width(width)
            .height(height)
            .background(
                color = color,
                shape = RoundedCornerShape(size = height / 2)
            )
    )
}

