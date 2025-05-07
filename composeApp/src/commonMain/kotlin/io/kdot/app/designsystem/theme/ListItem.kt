/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.theme

import androidx.compose.foundation.clickable
import androidx.compose.material3.ListItemColors
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.LocalContentColor
import androidx.compose.material3.LocalTextStyle
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kdot.app.designsystem.components.list.ListItemContent
import io.kdot.app.ui.theme.appColors
import io.kdot.app.ui.theme.appTypography

// Designs: https://www.figma.com/file/G1xy0HDZKJf5TCRFmKb5d5/Compound-Android-Components?type=design&node-id=425%3A24208&mode=design&t=G5hCfkLB6GgXDuWe-1

/**
 * A List Item component to be used in lists and menus with simple layouts, matching the Material 3 guidelines.
 * @param headlineContent The main content of the list item, usually a text.
 * @param modifier The modifier to be applied to the list item.
 * @param supportingContent The content to be displayed below the headline content.
 * @param leadingContent The content to be displayed before the headline content.
 * @param trailingContent The content to be displayed after the headline content.
 * @param style The style to use for the list item. This may change the color and text styles of the contents. [ListItemStyle.Default] is used by default.
 * @param enabled Whether the list item is enabled. When disabled, will change the color of the headline content and the leading content to use disabled tokens.
 * @param alwaysClickable Whether the list item should always be clickable, even when disabled.
 * @param onClick The callback to be called when the list item is clicked.
 */
@Suppress("LongParameterList")
@Composable
fun ListItem(
    headlineContent: @Composable () -> Unit,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: ListItemContent? = null,
    trailingContent: ListItemContent? = null,
    style: ListItemStyle = ListItemStyle.Default,
    enabled: Boolean = true,
    alwaysClickable: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    val colors = ListItemDefaults.colors(
        containerColor = Color.Transparent,
        headlineColor = style.headlineColor(),
        leadingIconColor = style.leadingIconColor(),
        trailingIconColor = style.trailingIconColor(),
        supportingColor = style.supportingTextColor(),
        disabledHeadlineColor = ListItemDefaultColors.headlineDisabled,
        disabledLeadingIconColor = ListItemDefaultColors.iconDisabled,
        disabledTrailingIconColor = ListItemDefaultColors.iconDisabled,
    )
    ListItem(
        headlineContent = headlineContent,
        modifier = modifier,
        supportingContent = supportingContent,
        leadingContent = leadingContent,
        trailingContent = trailingContent,
        colors = colors,
        enabled = enabled,
        alwaysClickable = alwaysClickable,
        onClick = onClick,
    )
}

/**
 * A List Item component to be used in lists and menus with simple layouts, matching the Material 3 guidelines.
 * @param headlineContent The main content of the list item, usually a text.
 * @param colors The colors to use for the list item. You can use [ListItemDefaults.colors] to create this.
 * @param modifier The modifier to be applied to the list item.
 * @param supportingContent The content to be displayed below the headline content.
 * @param leadingContent The content to be displayed before the headline content.
 * @param trailingContent The content to be displayed after the headline content.
 * @param enabled Whether the list item is enabled. When disabled, will change the color of the headline content and the leading content to use disabled tokens.
 * @param alwaysClickable Whether the list item should always be clickable, even when disabled.
 * @param onClick The callback to be called when the list item is clicked.
 */
@Suppress("LongParameterList")
@Composable
fun ListItem(
    headlineContent: @Composable () -> Unit,
    colors: ListItemColors,
    modifier: Modifier = Modifier,
    supportingContent: @Composable (() -> Unit)? = null,
    leadingContent: ListItemContent? = null,
    trailingContent: ListItemContent? = null,
    enabled: Boolean = true,
    alwaysClickable: Boolean = false,
    onClick: (() -> Unit)? = null,
) {
    // We cannot just pass the disabled colors, they must be set manually: https://issuetracker.google.com/issues/280480132
    val headlineColor = if (enabled) colors.headlineColor else colors.disabledHeadlineColor
    val supportingColor = if (enabled) colors.supportingTextColor else colors.disabledHeadlineColor.copy(alpha = 0.80f)
    val leadingContentColor = if (enabled) colors.leadingIconColor else colors.disabledLeadingIconColor
    val trailingContentColor = if (enabled) colors.trailingIconColor else colors.disabledTrailingIconColor

    val decoratedHeadlineContent: @Composable () -> Unit = {
        CompositionLocalProvider(
            LocalTextStyle provides MaterialTheme.typography.bodyLarge,
            LocalContentColor provides headlineColor,
        ) {
            headlineContent()
        }
    }
    val decoratedSupportingContent: (@Composable () -> Unit)? = supportingContent?.let { content ->
        {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.typography.bodyMedium,
                LocalContentColor provides supportingColor,
            ) {
                content()
            }
        }
    }
    val decoratedLeadingContent: (@Composable () -> Unit)? = leadingContent?.let { content ->
        {
            CompositionLocalProvider(
                LocalContentColor provides leadingContentColor,
            ) {
                content.View()
            }
        }
    }
    val decoratedTrailingContent: (@Composable () -> Unit)? = trailingContent?.let { content ->
        {
            CompositionLocalProvider(
                LocalTextStyle provides MaterialTheme.appTypography.fontBodyMdRegular,
                LocalContentColor provides trailingContentColor,
            ) {
                content.View()
            }
        }
    }

    androidx.compose.material3.ListItem(
        headlineContent = decoratedHeadlineContent,
        modifier = if (onClick != null) {
            Modifier
                .clickable(enabled = enabled || alwaysClickable, onClick = onClick)
                .then(modifier)
        } else {
            modifier
        },
        overlineContent = null,
        supportingContent = decoratedSupportingContent,
        leadingContent = decoratedLeadingContent,
        trailingContent = decoratedTrailingContent,
        colors = colors,
        tonalElevation = 0.dp,
        shadowElevation = 0.dp,
    )
}

/**
 * The style to use for a [ListItem].
 */
@Immutable
sealed interface ListItemStyle {
    data object Default : ListItemStyle
    data object Primary : ListItemStyle
    data object Destructive : ListItemStyle

    @Composable
    fun headlineColor() = when (this) {
        Default, Primary -> ListItemDefaultColors.headline
        Destructive -> MaterialTheme.appColors.textCriticalPrimary
    }

    @Composable
    fun supportingTextColor() = when (this) {
        Default, Primary -> ListItemDefaultColors.supportingText
        // FIXME once we have a defined color for this value
        Destructive -> MaterialTheme.appColors.textCriticalPrimary.copy(alpha = 0.8f)
    }

    @Composable
    fun leadingIconColor() = when (this) {
        Default -> ListItemDefaultColors.icon
        Primary -> MaterialTheme.appColors.iconPrimary
        Destructive -> MaterialTheme.appColors.iconCriticalPrimary
    }

    @Composable
    fun trailingIconColor() = when (this) {
        Default -> ListItemDefaultColors.icon
        Primary -> MaterialTheme.appColors.iconPrimary
        Destructive -> MaterialTheme.appColors.iconCriticalPrimary
    }
}

object ListItemDefaultColors {
    val headline: Color @Composable get() = MaterialTheme.appColors.textPrimary
    val headlineDisabled: Color @Composable get() = MaterialTheme.appColors.textDisabled
    val supportingText: Color @Composable get() = MaterialTheme.colorScheme.onSurfaceVariant
    val icon: Color @Composable get() = MaterialTheme.appColors.iconTertiary
    val iconDisabled: Color @Composable get() = MaterialTheme.appColors.iconDisabled

    val colors: ListItemColors
        @Composable get() = ListItemDefaults.colors(
            headlineColor = headline,
            supportingColor = supportingText,
            leadingIconColor = icon,
            trailingIconColor = icon,
            disabledHeadlineColor = headlineDisabled,
            disabledLeadingIconColor = iconDisabled,
            disabledTrailingIconColor = iconDisabled,
        )
}
