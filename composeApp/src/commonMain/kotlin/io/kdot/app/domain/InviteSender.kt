/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.domain

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.font.FontWeight
import io.kdot.app.designsystem.Resources
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
        return stringResource(
            Resources.String.screen_invites_invited_you,
            displayName,
            userId.full
        ).let { text ->
            val senderNameStart = stringResource(
                Resources.String.screen_invites_invited_you,
                displayName,
                userId.full
            ).indexOf("%1\$s")
            AnnotatedString(
                text = text,
                spanStyles = listOf(
                    AnnotatedString.Range(
                        SpanStyle(
                            fontWeight = FontWeight.Medium
                        ),
                        start = senderNameStart,
                        end = senderNameStart + displayName.length
                    )
                )
            )
        }
    }
}


