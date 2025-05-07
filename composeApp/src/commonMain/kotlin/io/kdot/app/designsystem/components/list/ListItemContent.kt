package io.kdot.app.designsystem.components.list

import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.material3.Badge
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Icon
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import io.kdot.app.ui.roomlist.IconSource
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

/**
 * This is a helper to set default leading and trailing content for [ListItem]s.
 */
@Immutable
sealed interface ListItemContent {
    /**
     * Default Switch content for [ListItem].
     * @param checked The current state of the switch.
     * @param onChange Callback when the switch is toggled: it should only be set to override the default click behaviour in the [ListItem].
     * @param enabled Whether the switch is enabled or not.
     */
    data class Switch(
        val checked: Boolean,
        val onChange: ((Boolean) -> Unit)? = null,
        val enabled: Boolean = true
    ) : ListItemContent

    /**
     * Default Checkbox content for [ListItem].
     * @param checked The current state of the checkbox.
     * @param onChange Callback when the checkbox is toggled: it should only be set to override the default click behaviour in the [ListItem].
     * @param enabled Whether the checkbox is enabled or not.
     * @param compact Reduces the size of the component to make the wrapping [ListItem] smaller.
     * This is especially useful when the [ListItem] is used inside a Dialog. `false` by default.
     */
    data class Checkbox(
        val checked: Boolean,
        val onChange: ((Boolean) -> Unit)? = null,
        val enabled: Boolean = true,
        val compact: Boolean = false
    ) : ListItemContent

    /**
     * Default RadioButton content for [ListItem].
     * @param selected The current state of the radio button.
     * @param onClick Callback when the radio button is toggled: it should only be set to override the default click behaviour in the [ListItem].
     * @param enabled Whether the radio button is enabled or not.
     * @param compact Reduces the size of the component to make the wrapping [ListItem] smaller.
     * This is especially useful when the [ListItem] is used inside a Dialog. `false` by default.
     */
    data class RadioButton(
        val selected: Boolean,
        val onClick: (() -> Unit)? = null,
        val enabled: Boolean = true,
        val compact: Boolean = false
    ) : ListItemContent

    /**
     * Default Icon content for [ListItem]. Sets the Icon component to a predefined size.
     * @param iconSource The icon to display, using [IconSource.getPainter].
     */
    data class Icon(val iconSource: IconSource, val contentDescription: String? = null) :
        ListItemContent

    /**
     * Default Text content for [ListItem]. Sets the Text component to a max size and clips overflow.
     * @param text The text to display.
     */
    data class Text(val text: String) : ListItemContent

    /** Displays any custom content. */
    data class Custom(val content: @Composable () -> Unit) : ListItemContent

    /** Displays a badge. */
    data object Badge : ListItemContent

    /** Displays a counter. */
    data class Counter(val count: Int) : ListItemContent

    @Composable
    fun View() {
        when (this) {
            is Switch -> Switch(
                checked = checked,
                onCheckedChange = onChange,
                enabled = enabled
            )

            is Checkbox -> Checkbox(
                modifier = if (compact) Modifier.size(maxCompactSize) else Modifier,
                checked = checked,
                onCheckedChange = onChange,
                enabled = enabled
            )

            is RadioButton -> RadioButton(
                modifier = if (compact) Modifier.size(maxCompactSize) else Modifier,
                selected = selected,
                onClick = onClick,
                enabled = enabled
            )

            is Icon -> {
                when (iconSource) {
                    is IconSource.Vector -> Icon(
                        modifier = Modifier.size(maxCompactSize),
                        imageVector = iconSource.imageVector,
                        contentDescription = contentDescription
                    )

                    is IconSource.Resources -> Icon(
                        modifier = Modifier.size(maxCompactSize),
                        painter = painterResource(iconSource.drawableResource),
                        contentDescription = contentDescription
                    )
                }

            }

            is Text -> Text(
                modifier = Modifier.widthIn(
                    max = 128.dp
                ), text = text, maxLines = 1, overflow = TextOverflow.Ellipsis
            )

            is Badge -> Badge(
                modifier = Modifier.size(maxCompactSize),
            )

            is Counter -> Badge(
                modifier = Modifier.size(maxCompactSize),
            ) {
                Text(text = count.toString())
            }

            is Custom -> content()
        }
    }
}

private val maxCompactSize = DpSize(24.dp, 24.dp)