/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.ui.roomlist

import androidx.compose.foundation.combinedClickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Arrangement.Absolute.spacedBy
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.VideoCall
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.ripple
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.element.android.features.roomlist.impl.model.RoomSummaryDisplayType
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.atomic.atom.InviteSenderView
import io.kdot.app.designsystem.atomic.atom.UnreadIndicatorAtom
import io.kdot.app.designsystem.components.avatar.CompositeAvatar
import io.kdot.app.designsystem.theme.Button
import io.kdot.app.designsystem.theme.ButtonSize
import io.kdot.app.domain.InviteSender
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.ui.theme.appColors
import io.kdot.app.ui.theme.appTypography
import io.kdot.app.ui.theme.unreadIndicator
import net.folivo.trixnity.core.model.RoomAliasId
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

internal val minHeight = 84.dp

@Composable
internal fun RoomSummaryRow(
    room: RoomListRoomSummary,
    onClick: (RoomListRoomSummary) -> Unit,
    eventSinkRoomList: (RoomListEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        when (room.displayType) {
            RoomSummaryDisplayType.PLACEHOLDER -> {
                RoomSummaryPlaceholderRow()
            }

            RoomSummaryDisplayType.INVITE -> {
                RoomSummaryScaffoldRow(
                    room = room,
                    onClick = onClick,
                    onLongClick = {

                    },
                ) {
                    InviteNameAndIndicatorRow(name = room.name)
                    InviteSubtitle(
                        isDm = room.isDm,
                        inviteSender = room.inviteSender,
                        canonicalAlias = room.canonicalAlias
                    )
                    if (!room.isDm && room.inviteSender != null) {
                        Spacer(modifier = Modifier.height(4.dp))
                        InviteSenderView(
                            modifier = Modifier.fillMaxWidth(),
                            inviteSender = room.inviteSender,
                        )
                    }
                    Spacer(modifier = Modifier.height(12.dp))
                    InviteButtonsRow(
                        onAcceptClick = {
                            eventSinkRoomList(RoomListEvents.AcceptInvite(room))
                        },
                        onDeclineClick = {
                            eventSinkRoomList(RoomListEvents.DeclineInvite(room))
                        }
                    )
                }
            }

            RoomSummaryDisplayType.ROOM -> {
                RoomSummaryScaffoldRow(
                    room = room,
                    onClick = onClick,
                    onLongClick = {
                        eventSinkRoomList(RoomListEvents.ShowContextMenu(room))
                    },
                ) {
                    NameAndTimestampRow(
                        name = room.name,
                        timestamp = room.timestamp,
                        isHighlighted = room.isHighlighted
                    )
                    LastMessageAndIndicatorRow(room = room)
                }
            }

            RoomSummaryDisplayType.KNOCKED -> {
                RoomSummaryScaffoldRow(
                    room = room,
                    onClick = onClick,
                    onLongClick = {

                    },
                ) {
                    NameAndTimestampRow(
                        name = room.name,
                        timestamp = null,
                        isHighlighted = room.isHighlighted
                    )
                    if (room.canonicalAlias != null) {
                        Text(
                            text = room.canonicalAlias.full,
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            style = MaterialTheme.appTypography.fontBodyMdRegular,
                            color = MaterialTheme.appColors.textSecondary,
                        )
                        Spacer(modifier = Modifier.height(4.dp))
                    }
                    Text(
                        text = stringResource(Resources.String.screen_join_room_knock_sent_title),
                        maxLines = 1,
                        overflow = TextOverflow.Ellipsis,
                        style = MaterialTheme.appTypography.fontBodyMdRegular,
                        color = MaterialTheme.appColors.textSecondary,
                    )
                }
            }
        }
    }
}

@Composable
private fun RoomSummaryScaffoldRow(
    room: RoomListRoomSummary,
    onClick: (RoomListRoomSummary) -> Unit,
    onLongClick: (RoomListRoomSummary) -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val clickModifier = Modifier.combinedClickable(
        onClick = { onClick(room) },
        onLongClick = { onLongClick(room) },
        indication = ripple(),
        interactionSource = remember { MutableInteractionSource() }
    )

    Row(
        modifier = modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .then(clickModifier)
            .padding(horizontal = 16.dp, vertical = 11.dp)
            .height(IntrinsicSize.Min),
    ) {
        CompositeAvatar(
            avatarData = room.avatarData,
            heroes = room.heroes,
        )
        Spacer(modifier = Modifier.width(16.dp))
        Column(
            modifier = Modifier.fillMaxWidth(),
            content = content,
        )
    }
}

@Composable
private fun NameAndTimestampRow(
    name: String?,
    timestamp: String?,
    isHighlighted: Boolean,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = spacedBy(16.dp)
    ) {
        // Name
        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.appTypography.fontBodyLgMedium,
            text = name ?: stringResource(Resources.String.common_no_room_name),
            fontStyle = FontStyle.Italic.takeIf { name == null },
            color = MaterialTheme.appColors.textSecondary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        // Timestamp
        Text(
            text = timestamp ?: "",
            style = MaterialTheme.appTypography.fontBodySmMedium,
            color = if (isHighlighted) {
                MaterialTheme.appColors.unreadIndicator
            } else {
                MaterialTheme.appColors.textSecondary
            },
        )
    }
}

@Composable
private fun InviteSubtitle(
    isDm: Boolean,
    inviteSender: InviteSender?,
    canonicalAlias: RoomAliasId?,
    modifier: Modifier = Modifier
) {
    val subtitle = if (isDm) {
        inviteSender?.userId?.domain
    } else {
        canonicalAlias?.full
    }
    if (subtitle != null) {
        Text(
            text = subtitle,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.appTypography.fontBodyMdRegular,
            color = MaterialTheme.appColors.textSecondary,
            modifier = modifier,
        )
    }
}

@Composable
private fun LastMessageAndIndicatorRow(
    room: RoomListRoomSummary,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = spacedBy(28.dp)
    ) {
        // Last Message
        val attributedLastMessage = room.lastMessage as? AnnotatedString
            ?: AnnotatedString(room.lastMessage?.toString().orEmpty())
        Text(
            modifier = Modifier.weight(1f),
            text = attributedLastMessage,
            color = MaterialTheme.appColors.textSecondary,
            style = MaterialTheme.appTypography.fontBodyMdRegular,
            minLines = 2,
            maxLines = 2,
            overflow = TextOverflow.Ellipsis
        )
        // Call and unread
        Row(
            modifier = Modifier.height(16.dp),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            val tint =
                if (room.isHighlighted) MaterialTheme.appColors.unreadIndicator else MaterialTheme.appColors.iconQuaternary
            if (room.hasRoomCall) {
                OnGoingCallIcon(
                    color = tint,
                )
            }
            MentionIndicatorAtom()
            if (room.hasNewContent) {
                UnreadIndicatorAtom(
                    color = tint
                )
            }
        }
    }
}

@Composable
private fun InviteNameAndIndicatorRow(
    name: String?,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.fillMaxWidth(),
        horizontalArrangement = spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Text(
            modifier = Modifier.weight(1f),
            style = MaterialTheme.appTypography.fontBodyLgMedium,
            text = name ?: stringResource(Resources.String.common_no_room_name),
            fontStyle = FontStyle.Italic.takeIf { name == null },
            color = MaterialTheme.appColors.textPrimary,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis
        )
        UnreadIndicatorAtom(
            color = MaterialTheme.appColors.unreadIndicator
        )
    }
}

@Composable
private fun InviteButtonsRow(
    onAcceptClick: () -> Unit,
    onDeclineClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        horizontalArrangement = spacedBy(12.dp)
    ) {
        io.kdot.app.designsystem.theme.OutlinedButton(
            text = stringResource(Resources.String.action_decline),
            onClick = onDeclineClick,
            size = ButtonSize.MediumLowPadding,
            modifier = Modifier.weight(1f),
        )
        Button(
            text = stringResource(Resources.String.action_accept),
            onClick = onAcceptClick,
            size = ButtonSize.MediumLowPadding,
            modifier = Modifier.weight(1f),
        )
    }
}

@Composable
private fun OnGoingCallIcon(
    color: Color,
) {

    Icon(
        modifier = Modifier.size(16.dp),
        imageVector = Icons.Default.VideoCall,
        contentDescription = null,
        tint = color,
    )
}


@Composable
private fun MentionIndicatorAtom() {
    Icon(
        modifier = Modifier.size(16.dp),
        contentDescription = null,
        painter = painterResource(Resources.Icon.ic_compound_mention),
        tint = MaterialTheme.appColors.unreadIndicator,
    )
}

