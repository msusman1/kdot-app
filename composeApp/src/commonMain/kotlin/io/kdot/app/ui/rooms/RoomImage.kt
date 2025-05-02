package io.kdot.app.ui.rooms

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.MapsUgc
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.common.AvatarWithImage
import io.kdot.app.designsystem.common.AvatarWithPresence
import io.kdot.app.designsystem.common.avatarSize
import io.kdot.app.designsystem.common.placeholder
import io.kdot.app.designsystem.icons.PublicIcon
import net.folivo.trixnity.core.model.events.m.Presence
import org.jetbrains.compose.resources.stringResource

@Composable
fun RoomImage(
    isInvite: Boolean?,
    roomImage: ByteArray,
    roomImageInitials: String?,
    presence: Presence?,
    isPublic: Boolean
) {

    if (isInvite == null || roomImageInitials == null) {
        Box(
            Modifier
                .width(avatarSize().dp)
                .height(avatarSize().dp)
                .placeholder(
                    visible = true,
                    color = Color.LightGray,
                    shape = CircleShape,
                )
        )
    } else {
        if (isInvite) AvatarWithImage {
            Icon(Icons.Default.MapsUgc, stringResource(Resources.String.join))
        }
        else Box {
            AvatarWithPresence(
                roomImage,
                roomImageInitials,
                presence
            )
            if (isPublic) PublicIcon()
        }
    }
}
