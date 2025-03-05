package io.kdot.app.designSystem.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp
import kdotapp.composeapp.generated.resources.Res
import kdotapp.composeapp.generated.resources.common_error
import kdotapp.composeapp.generated.resources.common_success
import org.jetbrains.compose.resources.stringResource

/**
 * Compound component that display a big icon centered in a rounded square.
 * Figma: https://www.figma.com/design/G1xy0HDZKJf5TCRFmKb5d5/Compound-Android-Components?node-id=1960-553&node-type=frame&m=dev
 */
object BigIcon {
    /**
     * The style of the [BigIcon].
     */
    @Immutable
    sealed interface Style {
        /**
         * The default style.
         *
         * @param vectorIcon the [ImageVector] to display
         * @param contentDescription the content description of the icon, if any. It defaults to `null`
         */
        data class Default(val vectorIcon: ImageVector, val contentDescription: String? = null) :
            Style

        /**
         * An alert style with a transparent background.
         */
        data object Alert : Style

        /**
         * An alert style with a tinted background.
         */
        data object AlertSolid : Style

        /**
         * A success style with a transparent background.
         */
        data object Success : Style

        /**
         * A success style with a tinted background.
         */
        data object SuccessSolid : Style
    }

    /**
     * Display a [BigIcon].
     *
     * @param style the style of the icon
     * @param modifier the modifier to apply to this layout
     */
    @Composable
    operator fun invoke(
        style: Style,
        modifier: Modifier = Modifier,
    ) {
        val backgroundColor = when (style) {
            is Style.Default -> MaterialTheme.colorScheme.secondaryContainer
            Style.Alert, Style.Success -> Color.Transparent
            Style.AlertSolid -> MaterialTheme.colorScheme.errorContainer
            Style.SuccessSolid -> MaterialTheme.colorScheme.surfaceContainer
        }
        val icon = when (style) {
            is Style.Default -> style.vectorIcon
            Style.Alert, Style.AlertSolid -> Icons.Default.Warning
            Style.Success, Style.SuccessSolid -> Icons.Default.CheckCircle
        }
        val contentDescription = when (style) {
            is Style.Default -> style.contentDescription
            Style.Alert, Style.AlertSolid -> stringResource(Res.string.common_error)
            Style.Success, Style.SuccessSolid -> stringResource(Res.string.common_success)
        }
        val iconTint = when (style) {
            is Style.Default -> MaterialTheme.colorScheme.secondary
            Style.Alert, Style.AlertSolid -> MaterialTheme.colorScheme.error
            Style.Success, Style.SuccessSolid -> MaterialTheme.colorScheme.primary
        }
        Box(
            modifier = modifier
                .size(64.dp)
                .clip(RoundedCornerShape(14.dp))
                .background(backgroundColor),
            contentAlignment = Alignment.Center,
        ) {
            Icon(
                modifier = Modifier.size(32.dp),
                tint = iconTint,
                imageVector = icon,
                contentDescription = contentDescription
            )
        }
    }
}
