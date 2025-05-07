package io.kdot.app.designsystem.colors

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import io.kdot.app.ui.theme.appColors

data class AvatarColors(
    /** Background color for the avatar. */
    val background: Color,
    /** Foreground color for the avatar. */
    val foreground: Color,
)

@Composable
fun avatarColors(): List<AvatarColors> {
    return listOf(
        AvatarColors(background = MaterialTheme.appColors.bgDecorative1, foreground = MaterialTheme.appColors.textDecorative1),
        AvatarColors(background = MaterialTheme.appColors.bgDecorative2, foreground = MaterialTheme.appColors.textDecorative2),
        AvatarColors(background = MaterialTheme.appColors.bgDecorative3, foreground = MaterialTheme.appColors.textDecorative3),
        AvatarColors(background = MaterialTheme.appColors.bgDecorative4, foreground = MaterialTheme.appColors.textDecorative4),
        AvatarColors(background = MaterialTheme.appColors.bgDecorative5, foreground = MaterialTheme.appColors.textDecorative5),
        AvatarColors(background = MaterialTheme.appColors.bgDecorative6, foreground = MaterialTheme.appColors.textDecorative6),
    )
}