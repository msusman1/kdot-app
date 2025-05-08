/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.components.buttons

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.LinearGradientShader
import androidx.compose.ui.graphics.Shader
import androidx.compose.ui.graphics.ShaderBrush
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.dp
import io.kdot.app.designsystem.theme.ButtonSize
import io.kdot.app.designsystem.theme.lowHorizontalPaddingValue
import io.kdot.app.ui.theme.appColors
import io.kdot.app.ui.theme.appTypography
import io.kdot.app.ui.theme.superButtonShaderColors

@Composable
fun SuperButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    shape: Shape = RoundedCornerShape(50),
    buttonSize: ButtonSize = ButtonSize.Large,
    enabled: Boolean = true,
    content: @Composable () -> Unit,
) {
    val contentPadding = remember(buttonSize) {
        when (buttonSize) {
            ButtonSize.Large -> PaddingValues(horizontal = 24.dp, vertical = 13.dp)
            ButtonSize.LargeLowPadding -> PaddingValues(
                horizontal = lowHorizontalPaddingValue,
                vertical = 13.dp
            )

            ButtonSize.Medium -> PaddingValues(horizontal = 20.dp, vertical = 9.dp)
            ButtonSize.MediumLowPadding -> PaddingValues(
                horizontal = lowHorizontalPaddingValue,
                vertical = 9.dp
            )

            ButtonSize.Small -> PaddingValues(horizontal = 16.dp, vertical = 5.dp)
        }
    }
    val superButtonShaderColors = MaterialTheme.appColors.superButtonShaderColors

    val shaderBrush = remember {
        object : ShaderBrush() {
            override fun createShader(size: Size): Shader {
                return LinearGradientShader(
                    from = Offset(0f, size.height),
                    to = Offset(size.width, 0f),
                    colors = superButtonShaderColors
                )
            }
        }
    }
    val border = if (enabled) {
        BorderStroke(1.dp, shaderBrush)
    } else {
        BorderStroke(1.dp, MaterialTheme.appColors.borderDisabled)
    }
    val backgroundColor = MaterialTheme.appColors.bgCanvasDefault
    Box(
        modifier = modifier
            .minimumInteractiveComponentSize()
            .graphicsLayer(shape = shape, clip = false)
            .clip(shape)
            .border(border, shape)
            .drawBehind {
                drawRect(backgroundColor)
                drawRect(brush = shaderBrush, alpha = 0.04f)
            }
            .clickable(
                enabled = enabled,
                onClick = onClick,
                interactionSource = remember { MutableInteractionSource() },
                indication = ripple()
            )
            .padding(contentPadding),
        contentAlignment = Alignment.Center
    ) {
        CompositionLocalProvider(
            LocalContentColor provides if (enabled) MaterialTheme.appColors.textPrimary else MaterialTheme.appColors.textDisabled,
            LocalTextStyle provides MaterialTheme.appTypography.fontBodyLgMedium,
        ) {
            content()
        }
    }
}
