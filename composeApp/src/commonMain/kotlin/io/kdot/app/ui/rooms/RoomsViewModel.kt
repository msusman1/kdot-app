package io.kdot.app.ui.rooms

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kdot.app.matrix.MatrixClientProvider
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.clientserverapi.client.SyncState
import net.folivo.trixnity.core.model.RoomId

class RoomsViewModel(
    private val clientProvider: MatrixClientProvider
) : ViewModel() {

    init {
        initializeSync()

    }

    val syncState: StateFlow<SyncState> = clientProvider.getClient()
        .syncState.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = SyncState.INITIAL_SYNC
        )
    val userAvatar: StateFlow<String?> = clientProvider.getClient()
        .avatarUrl.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = null
        )

    @OptIn(ExperimentalCoroutinesApi::class)
    val rooms: StateFlow<List<Room>> =
        clientProvider.getClient().room.getAll()
            .let { roomMapFlow: Flow<Map<RoomId, Flow<Room?>>> ->
                roomMapFlow.flatMapLatest { roomMap ->
                    val roomFlows = roomMap.values.map { it.filterNotNull() }
                    if (roomFlows.isEmpty()) {
                        flowOf(emptyList())
                    } else {
                        combine(roomFlows) { it.toList() }
                    }
                }
            }
            .stateIn(
                scope = viewModelScope,
                started = SharingStarted.WhileSubscribed(5_000),
                initialValue = emptyList()
            )

    private fun initializeSync() = viewModelScope.launch {
        clientProvider.getClient().startSync()
    }
}



