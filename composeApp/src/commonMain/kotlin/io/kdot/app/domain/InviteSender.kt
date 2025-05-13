/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.domain

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import io.kdot.app.designsystem.Resources
import io.kdot.app.ui.theme.appColors
import net.folivo.trixnity.core.model.UserId
import org.jetbrains.compose.resources.stringResource

@Immutable
data class InviteSender(
    val userId: UserId,
    val displayName: String,
    val avatarData: AvatarData,
    val membershipChangeReason: String?,
) {
    @Composable
    fun annotatedString(): AnnotatedString {
        val text = stringResource(
            Resources.String.screen_invites_invited_you,
            displayName,
            userId.full
        )
        val start = text.indexOf(displayName)
        return AnnotatedString(
            text = text,
            spanStyles = listOf(
                AnnotatedString.Range(
                    SpanStyle(
                        fontWeight = FontWeight.Medium,
                        color = MaterialTheme.appColors.textPrimary
                    ),
                    start = start,
                    end = start + displayName.length
                )
            )
        )
    }
}


