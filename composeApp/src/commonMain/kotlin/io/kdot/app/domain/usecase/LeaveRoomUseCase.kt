package io.kdot.app.domain.usecase

import io.github.aakira.napier.Napier
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.core.MatrixServerException
import net.folivo.trixnity.core.model.RoomId
import net.folivo.trixnity.core.model.events.m.room.Membership

class LeaveRoomUseCase {
    suspend operator fun invoke(client: MatrixClient, roomId: RoomId,forget: Boolean = true) = runCatching {
        val roomFlow: Flow<Room?> = client.room.getById(roomId)
        val room = roomFlow.first()
        if (room == null) {
            return@runCatching
        }
        Napier.d { "start leaving, forgetting and removing rooms $roomId" }
        if (room.membership != Membership.LEAVE) {
           val leaveResult= client.api.room.leaveRoom(roomId)
            val leaveException = leaveResult.exceptionOrNull()

            if (leaveException != null && leaveException !is MatrixServerException) {
                Napier.w(leaveException) { "skip forget room $roomId, because something went wrong (e. g. network error)" }
                throw leaveException
            }
        }

        if (forget) {
            Napier.d { "wait for room $roomId to be marked as LEAVE" }
            roomFlow.filter { it?.membership == Membership.LEAVE }.first()
            Napier.d { "forget room" }
            val forgetResult = client.api.room.forgetRoom(roomId)
            val forgetException = forgetResult.exceptionOrNull()

            if (forgetException != null && forgetException !is MatrixServerException) {
                Napier.w(forgetException) { "skip removing local copy of room $roomId, because something went wrong (e. g. network error)" }
                throw forgetException
            }
            Napier.d { "remove local copy of room $roomId" }
            client.room.forgetRoom(roomId)
        }

    }

}