/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.components.avatar

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.semantics.clearAndSetSemantics
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import coil3.compose.AsyncImage
import coil3.compose.AsyncImagePainter
import coil3.compose.SubcomposeAsyncImage
import coil3.compose.SubcomposeAsyncImageContent
import io.github.aakira.napier.Napier
import io.kdot.app.designsystem.colors.AvatarColorsProvider
import io.kdot.app.designsystem.text.toSp
import io.kdot.app.domain.AvatarData
import io.kdot.app.ui.theme.appTypography

@Composable
fun Avatar(
    avatarData: AvatarData,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
    // If not null, will be used instead of the size from avatarData
    forcedAvatarSize: Dp? = null,
) {
    val commonModifier = modifier
        .size(forcedAvatarSize ?: avatarData.size.dp)
        .clip(CircleShape)
    if (avatarData.url.isNullOrBlank()) {
        InitialsAvatar(
            avatarData = avatarData,
            forcedAvatarSize = forcedAvatarSize,
            modifier = commonModifier,
        )
    } else {
        ImageAvatar(
            avatarData = avatarData,
            forcedAvatarSize = forcedAvatarSize,
            modifier = commonModifier,
            contentDescription = contentDescription,
        )
    }
}

@Composable
private fun ImageAvatar(
    avatarData: AvatarData,
    forcedAvatarSize: Dp?,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {

    if (LocalInspectionMode.current) {
        // For compose previews, use debugPlaceholderAvatar()
        // instead of falling back to initials avatar on load failure
        AsyncImage(
            model = avatarData,
            contentDescription = contentDescription,
//            placeholder = debugPlaceholderAvatar(),
            modifier = modifier
        )
    } else {
        SubcomposeAsyncImage(
            model = avatarData,
            contentDescription = contentDescription,
            contentScale = ContentScale.Crop,
            modifier = modifier
        ) {
            val painterState by painter.state.collectAsStateWithLifecycle()
            when (val state = painterState) {
                is AsyncImagePainter.State.Success -> SubcomposeAsyncImageContent()
                is AsyncImagePainter.State.Error -> {
                    SideEffect {
                        Napier.d("AsyncImagePainter Throwable: ${state.result.throwable} userId:${avatarData.id}")
                    }
                    InitialsAvatar(
                        avatarData = avatarData,
                        forcedAvatarSize = forcedAvatarSize,
                    )
                }

                else -> InitialsAvatar(
                    avatarData = avatarData,
                    forcedAvatarSize = forcedAvatarSize,
                )
            }
        }
    }
}

@Composable
private fun InitialsAvatar(
    avatarData: AvatarData,
    forcedAvatarSize: Dp?,
    modifier: Modifier = Modifier,
) {
    val avatarColors = AvatarColorsProvider.provide(avatarData.id)
    Box(
        modifier.background(color = avatarColors.background)
    ) {
        val fontSize = (forcedAvatarSize ?: avatarData.size.dp).toSp() / 2
        val originalFont = MaterialTheme.appTypography.fontHeadingMdBold
        val ratio = fontSize.value / originalFont.fontSize.value
        val lineHeight = originalFont.lineHeight * ratio
        Text(
            modifier = Modifier
                .clearAndSetSemantics {}
                .align(Alignment.Center),
            text = avatarData.initial,
            style = originalFont.copy(
                fontSize = fontSize,
                lineHeight = lineHeight,
                letterSpacing = 0.sp
            ),
            color = avatarColors.foreground,
        )
    }
}

