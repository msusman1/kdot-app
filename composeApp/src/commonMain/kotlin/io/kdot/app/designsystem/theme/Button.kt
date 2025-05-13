/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.theme

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.progressSemantics
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.graphics.isSpecified
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.kdot.app.designsystem.components.CircularProgressIndicator
import io.kdot.app.ui.theme.appColors
import io.kdot.app.ui.theme.appTypography
import kdotapp.composeapp.generated.resources.Res
import kdotapp.composeapp.generated.resources.app_logo
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

// Designs: https://www.figma.com/file/G1xy0HDZKJf5TCRFmKb5d5/Compound-Android-Components?type=design&mode=design&t=U03tOFZz5FSLVUMa-1

// Horizontal padding for button with low padding
internal val lowHorizontalPaddingValue = 4.dp

@Composable
fun Button(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: ButtonSize = ButtonSize.Large,
    showProgress: Boolean = false,
    destructive: Boolean = false,
    leadingIcon: DrawableResource? = null,
) = ButtonInternal(
    text = text,
    onClick = onClick,
    style = ButtonStyle.Filled,
    modifier = modifier,
    enabled = enabled,
    size = size,
    showProgress = showProgress,
    destructive = destructive,
    leadingIcon = leadingIcon
)

@Composable
fun OutlinedButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: ButtonSize = ButtonSize.Large,
    showProgress: Boolean = false,
    destructive: Boolean = false,
    leadingIcon: DrawableResource? = null,
) = ButtonInternal(
    text = text,
    onClick = onClick,
    style = ButtonStyle.Outlined,
    modifier = modifier,
    enabled = enabled,
    size = size,
    showProgress = showProgress,
    destructive = destructive,
    leadingIcon = leadingIcon
)

@Composable
fun TextButton(
    text: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    size: ButtonSize = ButtonSize.Large,
    showProgress: Boolean = false,
    destructive: Boolean = false,
    leadingIcon: DrawableResource? = null,
) = ButtonInternal(
    text = text,
    onClick = onClick,
    style = ButtonStyle.Text,
    modifier = modifier,
    enabled = enabled,
    size = size,
    showProgress = showProgress,
    destructive = destructive,
    leadingIcon = leadingIcon
)

@Composable
fun InvisibleButton(
    modifier: Modifier = Modifier,
    size: ButtonSize = ButtonSize.Large,
) {
    Spacer(modifier = modifier.height(size.toMinHeight()))
}

@Composable
private fun ButtonInternal(
    text: String,
    onClick: () -> Unit,
    style: ButtonStyle,
    modifier: Modifier = Modifier,
    destructive: Boolean = false,
    colors: ButtonColors = style.getColors(destructive),
    enabled: Boolean = true,
    size: ButtonSize = ButtonSize.Large,
    showProgress: Boolean = false,
    leadingIcon: DrawableResource? = null,
) {
    val minHeight = size.toMinHeight()
    val hasStartDrawable = showProgress || leadingIcon != null

    val contentPadding = when (size) {
        ButtonSize.Small -> {
            if (hasStartDrawable) {
                PaddingValues(start = 8.dp, top = 5.dp, end = 16.dp, bottom = 5.dp)
            } else {
                PaddingValues(start = 16.dp, top = 5.dp, end = 16.dp, bottom = 5.dp)
            }
        }

        ButtonSize.Medium -> when (style) {
            ButtonStyle.Filled,
            ButtonStyle.Outlined -> if (hasStartDrawable) {
                PaddingValues(start = 16.dp, top = 10.dp, end = 24.dp, bottom = 10.dp)
            } else {
                PaddingValues(start = 24.dp, top = 10.dp, end = 24.dp, bottom = 10.dp)
            }

            ButtonStyle.Text -> if (hasStartDrawable) {
                PaddingValues(start = 12.dp, top = 10.dp, end = 16.dp, bottom = 10.dp)
            } else {
                PaddingValues(start = 12.dp, top = 10.dp, end = 12.dp, bottom = 10.dp)
            }
        }

        ButtonSize.MediumLowPadding -> PaddingValues(
            horizontal = lowHorizontalPaddingValue,
            vertical = 10.dp
        )

        ButtonSize.Large -> when (style) {
            ButtonStyle.Filled,
            ButtonStyle.Outlined -> if (hasStartDrawable) {
                PaddingValues(start = 24.dp, top = 13.dp, end = 32.dp, bottom = 13.dp)
            } else {
                PaddingValues(start = 32.dp, top = 13.dp, end = 32.dp, bottom = 13.dp)
            }

            ButtonStyle.Text -> if (hasStartDrawable) {
                PaddingValues(start = 12.dp, top = 13.dp, end = 16.dp, bottom = 13.dp)
            } else {
                PaddingValues(start = 16.dp, top = 13.dp, end = 16.dp, bottom = 13.dp)
            }
        }

        ButtonSize.LargeLowPadding -> PaddingValues(
            horizontal = lowHorizontalPaddingValue,
            vertical = 13.dp
        )
    }

    val shape = when (style) {
        ButtonStyle.Filled,
        ButtonStyle.Outlined -> RoundedCornerShape(percent = 50)

        ButtonStyle.Text -> RectangleShape
    }

    val border = when (style) {
        ButtonStyle.Filled -> null
        ButtonStyle.Outlined -> BorderStroke(
            width = 1.dp,
            color = if (destructive) {
                MaterialTheme.appColors.borderCriticalPrimary.copy(
                    alpha = if (enabled) 1f else 0.5f
                )
            } else {
                MaterialTheme.appColors.borderInteractiveSecondary
            }
        )

        ButtonStyle.Text -> null
    }

    androidx.compose.material3.Button(
        onClick = {
            if (!showProgress) {
                onClick()
            }
        },
        modifier = modifier.heightIn(min = minHeight),
        enabled = enabled,
        shape = shape,
        colors = colors,
        elevation = null,
        border = border,
        contentPadding = contentPadding,
        interactionSource = remember { MutableInteractionSource() },
    ) {
        when {
            showProgress -> {
                CircularProgressIndicator(
                    modifier = Modifier
                        .progressSemantics()
                        .size(20.dp),
                    color = LocalContentColor.current,
                    strokeWidth = 2.dp,
                )
                Spacer(modifier = Modifier.width(8.dp))
            }

            leadingIcon != null -> {
                Icon(
                    painter = painterResource(leadingIcon),
                    contentDescription = null,
                    tint = LocalContentColor.current,
                    modifier = Modifier.size(20.dp),
                )
                Spacer(modifier = Modifier.width(8.dp))
            }
        }
        Text(
            text = text,
            style = MaterialTheme.appTypography.fontBodyLgMedium,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
        )
    }
}

private fun ButtonSize.toMinHeight() = when (this) {
    ButtonSize.Small -> 32.dp
    ButtonSize.Medium,
    ButtonSize.MediumLowPadding -> 40.dp

    ButtonSize.Large,
    ButtonSize.LargeLowPadding -> 48.dp
}


enum class ButtonSize {
    Small,
    Medium,

    /**
     * Like [Medium] but with minimal horizontal padding, so that large texts have less risk to get truncated.
     * To be used for instance for button with weight which ensures a maximal width.
     */
    MediumLowPadding,
    Large,

    /**
     * Like [Large] but with minimal horizontal padding, so that large texts have less risk to get truncated.
     * To be used for instance for button with weight which ensures a maximal width.
     */
    LargeLowPadding,
}

internal enum class ButtonStyle {
    Filled,
    Outlined,
    Text;

    @Composable
    fun getColors(destructive: Boolean): ButtonColors = when (this) {
        Filled -> ButtonDefaults.buttonColors(
            containerColor = getPrimaryColor(destructive),
            contentColor = MaterialTheme.colorScheme.onPrimary,
            disabledContainerColor = if (destructive) {
                MaterialTheme.appColors.bgCriticalPrimary.copy(alpha = 0.5f)
            } else {
                MaterialTheme.appColors.bgActionPrimaryDisabled
            },
            disabledContentColor = MaterialTheme.appColors.textOnSolidPrimary
        )

        Outlined -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = getPrimaryColor(destructive),
            disabledContainerColor = Color.Transparent,
            disabledContentColor = getDisabledContentColor(destructive),
        )

        Text -> ButtonDefaults.buttonColors(
            containerColor = Color.Transparent,
            contentColor = if (destructive) {
                MaterialTheme.appColors.textCriticalPrimary
            } else {
                if (LocalContentColor.current.isSpecified) LocalContentColor.current else MaterialTheme.appColors.textPrimary
            },
            disabledContainerColor = Color.Transparent,
            disabledContentColor = getDisabledContentColor(destructive),
        )
    }

    @Composable
    private fun getPrimaryColor(destructive: Boolean): Color {
        return if (destructive) {
            MaterialTheme.appColors.bgCriticalPrimary
        } else {
            MaterialTheme.colorScheme.primary
        }
    }

    @Composable
    private fun getDisabledContentColor(destructive: Boolean): Color {
        return if (destructive) {
            MaterialTheme.appColors.textCriticalPrimary.copy(alpha = 0.5f)
        } else {
            MaterialTheme.appColors.textDisabled
        }
    }
}

