package io.kdot.app.features.leaveroom

import io.kdot.app.matrix.MatrixClientProvider
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.store.joinedMemberCount
import net.folivo.trixnity.core.model.RoomId


class LeaveRoomStateHolder(
    private val clientProvider: MatrixClientProvider,
    private val scope: CoroutineScope
) {

    private val _leaveRoomStateFlow = MutableStateFlow(
        LeaveRoomState(
            confirmation = LeaveRoomState.Confirmation.Hidden,
            progress = LeaveRoomState.Progress.Hidden,
            error = LeaveRoomState.Error.Hidden,
        )
    )
    val leaveRoomStateFlow = _leaveRoomStateFlow.asStateFlow()

    fun handleEvent(event: LeaveRoomEvent) {
        when (event) {
            is LeaveRoomEvent.HideConfirmation -> {
                _leaveRoomStateFlow.update {
                    it.copy(
                        confirmation = LeaveRoomState.Confirmation.Hidden,
                    )
                }
            }

            is LeaveRoomEvent.HideError -> {
                _leaveRoomStateFlow.update {
                    it.copy(
                        error = LeaveRoomState.Error.Hidden,
                    )
                }
            }

            is LeaveRoomEvent.LeaveRoom -> scope.launch {
                clientProvider.getClient().leaveRoom(event.roomId)
            }

            is LeaveRoomEvent.ShowConfirmation -> scope.launch {

                clientProvider.getClient().showLeaveRoomAlert(event.roomId)
            }
        }
    }

    private suspend fun MatrixClient.showLeaveRoomAlert(roomId: RoomId) {
        val room = this.room.getById(roomId).firstOrNull()
        room?.let {
            val confirmation = when {
                it.isDirect -> LeaveRoomState.Confirmation.Dm(roomId)
                it.joinedMemberCount == 1L -> LeaveRoomState.Confirmation.LastUserInRoom(roomId)
                // todo Uncomment and adjust the condition below if you want to handle private rooms differently
                // !it.isPublic -> LeaveRoomState.Confirmation.PrivateRoom(roomId)
                else -> LeaveRoomState.Confirmation.Generic(roomId)
            }

            _leaveRoomStateFlow.update { state ->
                state.copy(confirmation = confirmation)
            }
        }

    }

    private suspend fun MatrixClient.leaveRoom(roomId: RoomId) {
        _leaveRoomStateFlow.update {
            it.copy(
                confirmation = LeaveRoomState.Confirmation.Hidden,
                progress = LeaveRoomState.Progress.Shown
            )
        }
//        this.room.forgetRoom(roomId)  todo check this
        delay(2000)
        _leaveRoomStateFlow.update {
            it.copy(
                progress = LeaveRoomState.Progress.Hidden
            )
        }
    }

}
