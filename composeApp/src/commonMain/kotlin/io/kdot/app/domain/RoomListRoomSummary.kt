/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.domain

import androidx.compose.runtime.Immutable
import io.element.android.features.roomlist.impl.model.RoomSummaryDisplayType
import io.kdot.app.designsystem.components.avatar.AvatarSize
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.core.model.Mention
import net.folivo.trixnity.core.model.RoomId

@Immutable
data class RoomListRoomSummary(
    val id: String,
    val displayType: RoomSummaryDisplayType,
    val roomId: RoomId,
    val name: String?,
    val canonicalAlias: Mention.RoomAlias?,
    val numberOfUnreadMessages: Long,
    val numberOfUnreadMentions: Long,
    val numberOfUnreadNotifications: Long,
    val isMarkedUnread: Boolean,
    val timestamp: String?,
    val lastMessage: CharSequence?,
    val avatarData: AvatarData,
    val hasRoomCall: Boolean,
    val isDirect: Boolean,
    val isDm: Boolean,
    val isFavorite: Boolean,
    val inviteSender: InviteSender?,
    val heroes: List<AvatarData>,
) {
    val isHighlighted = (numberOfUnreadNotifications > 0 || numberOfUnreadMentions > 0) ||
            isMarkedUnread ||
            displayType == RoomSummaryDisplayType.INVITE
    val hasNewContent = numberOfUnreadMessages > 0 ||
            numberOfUnreadMentions > 0 ||
            numberOfUnreadNotifications > 0 ||
            isMarkedUnread ||
            displayType == RoomSummaryDisplayType.INVITE
}

fun createRoomSummary(room: Room): RoomListRoomSummary {
    return RoomListRoomSummary(
        id = room.roomId.full,
        displayType = RoomSummaryDisplayType.ROOM,
        roomId = room.roomId,
        name = room.name?.explicitName,
        canonicalAlias = null,
        numberOfUnreadMessages = room.unreadMessageCount,
        numberOfUnreadMentions = 0,
        numberOfUnreadNotifications = 0,
        isMarkedUnread = room.markedUnread,
        timestamp = room.lastRelevantEventTimestamp?.toString(),
        lastMessage = null,
        avatarData = AvatarData(
            id = room.roomId.full,
            url = room.avatarUrl,
            name = room.name?.explicitName ?: "",
            size = AvatarSize.RoomListItem
        ),
        hasRoomCall = false,
        isDirect = room.isDirect,
        isDm = room.isDirect,
        isFavorite = false,
        inviteSender = null,
        heroes = emptyList()
    )
}
