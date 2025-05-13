package io.kdot.app.previews.roomlist

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import io.element.android.features.roomlist.impl.model.RoomSummaryDisplayType
import io.kdot.app.designsystem.components.avatar.AvatarSize
import io.kdot.app.domain.AvatarData
import io.kdot.app.domain.InviteSender
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.previews.KDotPreview
import io.kdot.app.previews.PreviewDayNight
import io.kdot.app.ui.roomlist.RoomSummaryRow
import net.folivo.trixnity.core.model.RoomAliasId
import net.folivo.trixnity.core.model.RoomId
import net.folivo.trixnity.core.model.UserId


open class RoomListRoomSummaryProvider : PreviewParameterProvider<RoomListRoomSummary> {
    override val values: Sequence<RoomListRoomSummary>
        get() = sequenceOf(
            listOf(
                aRoomListRoomSummary(displayType = RoomSummaryDisplayType.PLACEHOLDER),
                aRoomListRoomSummary(),
                aRoomListRoomSummary(name = null),
                aRoomListRoomSummary(lastMessage = null),
                aRoomListRoomSummary(
                    name = "A very long room name that should be truncated",
                    lastMessage = "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt" + " ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea com" + "modo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur.",
                    timestamp = "yesterday",
                    numberOfUnreadMessages = 1,
                ),
            ),
            listOf(false, true).map { hasCall ->
                listOf(
                    aRoomListRoomSummary(
                        lastMessage = "No activity" + if (hasCall) ", call" else "",
                        numberOfUnreadMessages = 0,
                        numberOfUnreadMentions = 0,
                        hasRoomCall = hasCall,
                    ),
                    aRoomListRoomSummary(
                        lastMessage = "New messages" + if (hasCall) ", call" else "",
                        numberOfUnreadMessages = 1,
                        numberOfUnreadMentions = 0,
                        hasRoomCall = hasCall,
                    ),
                    aRoomListRoomSummary(
                        lastMessage = "New messages, mentions" + if (hasCall) ", call" else "",
                        numberOfUnreadMessages = 1,
                        numberOfUnreadMentions = 1,
                        hasRoomCall = hasCall,
                    ),
                    aRoomListRoomSummary(
                        lastMessage = "New mentions" + if (hasCall) ", call" else "",
                        numberOfUnreadMessages = 0,
                        numberOfUnreadMentions = 1,
                        hasRoomCall = hasCall,
                    ),
                )

            }.flatten(),
            listOf(
                aRoomListRoomSummary(
                    displayType = RoomSummaryDisplayType.INVITE,
                    inviteSender = anInviteSender(
                        userId = UserId("@alice:matrix.org"),  //sdfasd
                        displayName = "Alice",
                    ),
                    canonicalAlias = RoomAliasId("#alias:matrix.org"),
                ),
                aRoomListRoomSummary(
                    name = "Bob",
                    displayType = RoomSummaryDisplayType.INVITE,
                    inviteSender = anInviteSender(
                        userId = UserId("@bob:matrix.org"),
                        displayName = "Bob",
                    ),
                    isDm = true,
                ),
                aRoomListRoomSummary(
                    name = null,
                    displayType = RoomSummaryDisplayType.INVITE,   //sadfsadfas
                    inviteSender = anInviteSender(
                        userId = UserId("@bob:matrix.org"),
                        displayName = "Bob",
                    ),
                ),
                aRoomListRoomSummary(
                    name = "A knocked room",
                    displayType = RoomSummaryDisplayType.KNOCKED,
                ),
                aRoomListRoomSummary(
                    name = "A knocked room with alias",
                    canonicalAlias = RoomAliasId("#knockable:matrix.org"),
                    displayType = RoomSummaryDisplayType.KNOCKED,
                )
            ),
        ).flatten()
}

internal fun anInviteSender(
    userId: UserId = UserId("@bob:domain"),
    displayName: String = "Bob",
    avatarData: AvatarData = AvatarData(userId.full, displayName, size = AvatarSize.InviteSender),
) = InviteSender(
    userId = userId,
    displayName = displayName,
    avatarData = avatarData,
    membershipChangeReason = null,
)

internal fun aRoomListRoomSummary(
    id: String = "!roomId:domain",
    name: String? = "Room name",
    numberOfUnreadMessages: Long = 0,
    numberOfUnreadMentions: Long = 0,
    numberOfUnreadNotifications: Long = 0,
    isMarkedUnread: Boolean = false,
    lastMessage: String? = "Last message",
    timestamp: String? = lastMessage?.let { "88:88" },
    hasRoomCall: Boolean = false,
    avatarData: AvatarData = AvatarData(id, name, size = AvatarSize.RoomListItem),
    isDirect: Boolean = false,
    isDm: Boolean = false,
    isFavorite: Boolean = false,
    inviteSender: InviteSender? = null,
    displayType: RoomSummaryDisplayType = RoomSummaryDisplayType.ROOM,
    canonicalAlias: RoomAliasId? = null,
    heroes: List<AvatarData> = emptyList(),
) = RoomListRoomSummary(
    id = id,
    roomId = RoomId(id),
    name = name,
    numberOfUnreadMessages = numberOfUnreadMessages,
    numberOfUnreadMentions = numberOfUnreadMentions,
    numberOfUnreadNotifications = numberOfUnreadNotifications,
    isMarkedUnread = isMarkedUnread,
    timestamp = timestamp,
    lastMessage = lastMessage,
    avatarData = avatarData,

    hasRoomCall = hasRoomCall,
    isDirect = isDirect,
    isDm = isDm,
    isFavorite = isFavorite,
    inviteSender = inviteSender,
    displayType = displayType,
    canonicalAlias = canonicalAlias,
    heroes = heroes.toList(),
)


@PreviewDayNight
@Composable
private fun RoomSummaryRowPreview(@PreviewParameter(RoomListRoomSummaryProvider::class) data: RoomListRoomSummary) {
    KDotPreview {
        RoomSummaryRow(
            room = data,
            onClick = {},
            eventSinkRoomList = {},
        )
    }
}