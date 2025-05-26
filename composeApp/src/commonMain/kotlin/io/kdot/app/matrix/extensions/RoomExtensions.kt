package io.kdot.app.matrix.extensions


import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import net.folivo.trixnity.client.room.RoomService
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.client.store.joinedMemberCount
import net.folivo.trixnity.core.model.RoomAliasId
import net.folivo.trixnity.core.model.RoomAliasId.Companion.sigilCharacter
import net.folivo.trixnity.core.model.UserId

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

fun isDm(isDirect: Boolean, activeMembersCount: Int): Boolean {
    return isDirect && activeMembersCount <= 2
}


val Room.isDm
    get() = isDm(
        isDirect,
        joinedMemberCount?.toInt() ?: 0
    )


val UserId.extractedDisplayName: String
    get() = full.removePrefix("@").substringBefore(":")


val RoomAliasId.localpart: String
    get() = full.trimStart(sigilCharacter).substringBefore(':')







