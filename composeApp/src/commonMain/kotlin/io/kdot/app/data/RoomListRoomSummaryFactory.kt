package io.kdot.app.data

import io.element.android.features.roomlist.impl.model.RoomSummaryDisplayType
import io.kdot.app.designsystem.components.avatar.AvatarSize
import io.kdot.app.domain.AvatarData
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.libraries.dateformatter.DateFormatter
import io.kdot.app.libraries.dateformatter.DateFormatterMode
import io.kdot.app.matrix.extensions.isDm
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.client.store.TimelineEvent
import net.folivo.trixnity.core.model.events.m.room.Membership


class RoomListRoomSummaryFactory(
    private val dateFormatter: DateFormatter,
    private val roomNameFormatter: RoomNameFormatter,
    private val roomLastMessageFormatter: RoomLastMessageFormatter,
    private val roomFavouriteRepository: RoomFavouriteRepository
) {
    @OptIn(ExperimentalCoroutinesApi::class)
    fun create(
        client: MatrixClient, room: Room
    ): Flow<RoomListRoomSummary> {


        val lastMessage: Flow<String> = room.lastRelevantEventId?.let { lastEventId ->
            client.room.getTimelineEvent(roomId = room.roomId, eventId = lastEventId)
                .distinctUntilChanged()
                .map { lastTimelineEvent: TimelineEvent? ->
                    if (lastTimelineEvent != null) {
                        roomLastMessageFormatter.format(
                            event = lastTimelineEvent,
                            isDirect = room.isDirect,
                            me = client.userId
                        )
                    } else {
                        ""
                    }
                }
        } ?: flowOf("")


        val roomDetailFlow: Flow<Room> = client.room.getById(room.roomId).filterNotNull()
        /*   val inviterFlow: Flow<ClientEvent.StateBaseEvent<MemberEventContent>?> =
               client.room.getState(
                   room.roomId,
                   eventContentClass = MemberEventContent::class,
                   stateKey = client.userId.full
               )*/
        /*  val roomAliasFLow = client.room
              .getState(room.roomId, eventContentClass = CanonicalAliasEventContent::class)



  */


        val isFavouriteFlow = roomFavouriteRepository.checkRoomFav(room.roomId)


        return combine(
            roomDetailFlow, lastMessage, isFavouriteFlow,   /*roomAliasFLow, , inviterFlow*/
        ) { roomDetail, lastMessage, isFavourite  /*roomAlias, , inviter*/ ->
            val roomName = roomNameFormatter.format(room)
            /*  val inviterSender =
                  inviter.takeIf { it != null && it.content.membership == Membership.INVITE }
                      ?.sender?.let {
                          client.api.user.getUserAvatar(it).toInviteSender()
                      }


              val heroes: List<AvatarData> = roomDetail.name?.heroes?.mapNotNull {
                  client.api.user.getUserAvatar(it).getAvatarData(AvatarSize.RoomListItem)
              }.orEmpty()*/


            RoomListRoomSummary(
                id = room.roomId.full,
                displayType = when (roomDetail.membership) {
                    Membership.JOIN -> RoomSummaryDisplayType.ROOM
                    Membership.INVITE -> RoomSummaryDisplayType.INVITE
                    Membership.KNOCK -> RoomSummaryDisplayType.KNOCKED
                    else -> RoomSummaryDisplayType.PLACEHOLDER
                },
                roomId = room.roomId,
                name = roomName,
                canonicalAlias = null,
                numberOfUnreadMessages = roomDetail.unreadMessageCount,
                numberOfUnreadMentions = 0,
                numberOfUnreadNotifications = 0,
                isMarkedUnread = room.markedUnread,
                timestamp = dateFormatter.format(
                    timestamp = room.lastRelevantEventTimestamp?.toEpochMilliseconds(),
                    mode = DateFormatterMode.TimeOrDate,
                    useRelative = true,
                ),
                lastMessage = lastMessage,
                avatarData = AvatarData(
                    id = room.roomId.full,
                    url = room.avatarUrl,
                    name = roomName,
                    size = AvatarSize.RoomListItem
                ),
                hasRoomCall = false,
                isDirect = room.isDirect,
                isDm = room.isDm,
                isFavorite = isFavourite,
                inviteSender = null,
                heroes = emptyList()
            )
        }
    }
}