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
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.client.store.RoomUser
import net.folivo.trixnity.client.store.TimelineEvent
import net.folivo.trixnity.client.user
import net.folivo.trixnity.core.model.RoomAliasId
import net.folivo.trixnity.core.model.RoomId
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.FileBased
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.Location
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.TextBased
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.Unknown
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.VerificationRequest
import net.folivo.trixnity.core.model.events.m.room.bodyWithoutFallback

@Immutable
data class RoomListRoomSummary(
    val id: String,
    val displayType: RoomSummaryDisplayType,
    val roomId: RoomId,
    val name: String?,
    val canonicalAlias: RoomAliasId?,
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

private fun timelineEventTypeDescription(event: TimelineEvent): String =
    event.content?.getOrNull().let { content ->
        when (content) {
            !is RoomMessageEventContent -> ""
            is FileBased.Image -> "Image"
            is FileBased.Video -> "Video"
            is FileBased.Audio -> "Audio"
            is FileBased.File -> "File"
            is TextBased,
            is Location,
            is VerificationRequest,
            is Unknown -> content.bodyWithoutFallback

            else -> ""
        }
    }

@OptIn(ExperimentalCoroutinesApi::class)
fun createRoomSummary(client: MatrixClient, room: Room): Flow<RoomListRoomSummary> {

    val eventFlow: Flow<TimelineEvent?> = if (room.lastRelevantEventId != null) {
        client.room.getTimelineEvent(roomId = room.roomId, eventId = room.lastRelevantEventId!!)
    } else {
        flowOf(null)
    }.distinctUntilChanged()

    val lastMessage: Flow<String> = eventFlow.flatMapLatest { lastTimelineEvent: TimelineEvent? ->
        if (lastTimelineEvent != null) {
            client.user.getById(
                roomId = room.roomId,
                userId = lastTimelineEvent.event.sender
            ).map { lastTimelineEventSender: RoomUser? ->
                val message = timelineEventTypeDescription(lastTimelineEvent)
                val isByMe = client.userId == lastTimelineEvent.event.sender
                val sender = if (isByMe) {
                    "you"
                } else {
                    lastTimelineEventSender?.name ?: lastTimelineEvent.event.sender.full
                }
                if (room.isDirect && isByMe.not()) message
                else "${sender}: $message"
            }
        } else {
            flowOf("")
        }
    }

    return lastMessage.map { lastMsg ->
        RoomListRoomSummary(
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
            lastMessage = lastMsg,
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
}
