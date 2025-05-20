package io.kdot.app.matrix.extensions


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import net.folivo.trixnity.client.room.RoomService
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.client.store.RoomDisplayName
import net.folivo.trixnity.client.store.TimelineEvent
import net.folivo.trixnity.core.model.RoomAliasId
import net.folivo.trixnity.core.model.RoomAliasId.Companion.sigilCharacter
import net.folivo.trixnity.core.model.UserId
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.FileBased
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.Location
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.TextBased
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.Unknown
import net.folivo.trixnity.core.model.events.m.room.RoomMessageEventContent.VerificationRequest
import net.folivo.trixnity.core.model.events.m.room.bodyWithoutFallback

@OptIn(ExperimentalCoroutinesApi::class)
fun RoomService.getAllFlatten(): Flow<List<Room>> {
    val rooms: Flow<List<Room>> = this.getAll()
        .flatMapLatest { roomsById ->
            val summaryFlows: List<Flow<Room>> = roomsById.values
                .map { roomFlow ->
                    roomFlow.filterNotNull()
                }
            if (summaryFlows.isEmpty()) {
                flowOf(emptyList())
            } else {
                combine(summaryFlows) { summariesArray ->
                    summariesArray.toList().reversed()
                }
            }
        }
    return rooms
}


val UserId.extractedDisplayName: String
    get() = full.removePrefix("@").substringBefore(":")


val RoomAliasId.localpart: String
    get() = full.trimStart(sigilCharacter).substringBefore(':')

private fun timelineEventTypeDescription(event: TimelineEvent): String =
    event.content?.getOrNull().let { content ->
        when (content) {
            !is RoomMessageEventContent -> ""
            is FileBased.Image -> "Image"
            is FileBased.Video -> "Video"
            is FileBased.Audio -> "Audio"
            is FileBased.File -> "File"
            is TextBased, is Location, is VerificationRequest, is Unknown -> content.bodyWithoutFallback

            else -> ""
        }
    }

class RoomLastMessageFormatter() {
    fun format(event: TimelineEvent, isDirect: Boolean, me: UserId): String {
        val senderName = event.event.sender.extractedDisplayName
        val message = timelineEventTypeDescription(event)
        val isByMe = me == event.event.sender
        val sender = if (isByMe) {
            "you"
        } else {
            senderName
        }
        return if (isDirect && isByMe.not()) message
        else if (message.isNotEmpty()) "${sender}: $message"
        else ""
    }
}

class RoomNameFormatter() {
    fun format(room: Room): String {
        val roomName: RoomDisplayName? = room.name
        val roomNameToUse = if (roomName != null) {
            val (explicitName, heroes, otherUsersCount, roomIsEmpty) = roomName
            val users: List<UserId> = heroes
            when {
                !explicitName.isNullOrEmpty() -> explicitName
                heroes.isEmpty() -> {
                    when {
                        roomIsEmpty -> "Empty chat"
                        otherUsersCount > 1 -> "$otherUsersCount persons"
                        else -> room.roomId.full
                    }
                }

                else -> {
                    val heroConcat = users.mapIndexed { index: Int, userId: UserId? ->
                        when {
                            otherUsersCount == 0 && index < heroes.size - 2 || otherUsersCount > 0L && index < heroes.size - 1 -> {
                                nameFromHeroes(heroes, index) + ", "
                            }

                            otherUsersCount == 0 && index == heroes.size - 2 -> {
                                nameFromHeroes(heroes, index) + " And "
                            }

                            otherUsersCount > 0L && index == heroes.size - 1 -> {
                                nameFromHeroes(heroes, index) + " And ${otherUsersCount} others"
                            }

                            else -> {
                                nameFromHeroes(heroes, index)
                            }
                        }
                    }.joinToString("")
                    if (roomIsEmpty) "Empty chat (was ${heroConcat})"
                    else heroConcat
                }
            }
        } else {
            room.roomId.full
        }

        return roomNameToUse
    }
}

fun nameFromHeroes(
    heroes: List<UserId>, index: Int
) = heroes[index].extractedDisplayName



