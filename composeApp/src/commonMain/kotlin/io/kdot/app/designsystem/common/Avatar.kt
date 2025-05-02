package io.kdot.app.designsystem.common

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Circle
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.max
import io.kdot.app.designsystem.Resources
import io.kdot.app.ui.theme.appColors
import io.kdot.app.utils.toImageBitmap
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.currentCoroutineContext
import kotlinx.coroutines.withContext
import net.folivo.trixnity.core.model.events.m.Presence
import org.jetbrains.compose.resources.stringResource


@Composable
fun Avatar(
    image: ByteArray?,
    initials: String,
    size: Dp = avatarSize().dp,
    overlay: @Composable (BoxScope.() -> Unit)? = null,
) {
    var imageBitmap by remember { mutableStateOf<ImageBitmap?>(null) }
    LaunchedEffect(image) {
        withContext(currentCoroutineContext() + Dispatchers.Default) {
            imageBitmap = image?.toImageBitmap()
        }
    }


    imageBitmap?.let { bitmap -> // toImageBitmap is the problem
        val maxScaleX = size / bitmap.width
        val maxScaleY = size / bitmap.height
        val scale = max(maxScaleX, maxScaleY)
        val width = scale * bitmap.width
        val height = scale * bitmap.height

        Box {
            AvatarWithImage(size) {
                Image(
                    bitmap,
                    stringResource(Resources.String.avatar),
                    Modifier.size(width, height),
                    contentScale = ContentScale.Crop
                )
            }
            overlay?.invoke(this)
        }
    } ?: run {
        Box {
            AvatarWithInitials(initials, size)
            overlay?.invoke(this)
        }
    }
}

@Composable
fun AvatarWithInitials(initials: String, size: Dp = avatarSize().dp) {
    AvatarBase(size) {
        Text(
            initials,
            textAlign = TextAlign.Center,
            fontSize = with(LocalDensity.current) { size.toSp() * 0.4 },
        )
    }
}

@Composable
fun AvatarWithImage(size: Dp = avatarSize().dp, content: @Composable BoxScope.() -> Unit) {
    AvatarBase(size) {
        Box(Modifier.align(Alignment.Center)) {
            content()
        }
    }
}

@Composable
fun AvatarWithPresence(image: ByteArray?, initials: String, presence: Presence?) {
    Avatar(image, initials) {
        when (presence) {
            null -> {}
            Presence.ONLINE -> PresenceIcon(
                stringResource(Resources.String.online),
                MaterialTheme.appColors.presenceOnline
            )

            Presence.OFFLINE -> PresenceIcon(
                stringResource(Resources.String.offline),
                MaterialTheme.appColors.presenceOffline
            )

            Presence.UNAVAILABLE -> PresenceIcon(
                stringResource(Resources.String.unavailable),
                MaterialTheme.appColors.presenceUnavailable
            )
        }
    }
}

@Composable
private fun AvatarBase(size: Dp = avatarSize().dp, content: @Composable BoxScope.() -> Unit) {
    Box(
        Modifier
            .size(size)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer)
            .border(2.dp, MaterialTheme.colorScheme.secondaryContainer, CircleShape)
            .wrapContentSize(Alignment.Center)
    ) {
        content()
    }
}

@Composable
private fun BoxScope.PresenceIcon(description: String, color: Color) {
    Box(Modifier.align(Alignment.BottomEnd), contentAlignment = Alignment.Center) {
        Icon(
            Icons.Default.Circle,
            description,
            Modifier.size(14.dp),
            tint = MaterialTheme.colorScheme.background,
        )
        Icon(
            Icons.Default.Circle,
            description,
            Modifier.size(10.dp),
            tint = color,
        )
    }
}
