package io.kdot.app.ui.roomlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kdot.app.designsystem.common.avatarSize
import io.kdot.app.designsystem.components.avatar.AvatarSize
import io.kdot.app.designsystem.utils.snackbar.SnackbarDispatcher
import io.kdot.app.domain.AvatarData
import io.kdot.app.features.leaveroom.LeaveRoomStateHolder
import io.kdot.app.matrix.MatrixClientProvider
import io.kdot.app.ui.roomlist.filter.RoomListFilterStateHolder
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.filterNotNull
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.folivo.trixnity.client.media
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.clientserverapi.client.SyncState
import net.folivo.trixnity.core.model.RoomId
import net.folivo.trixnity.utils.toByteArray


class RoomListViewModel(
    private val clientProvider: MatrixClientProvider
) : ViewModel() {
    val roomListFilterStateHolder = RoomListFilterStateHolder()
    val leaveRoomStateHolder = LeaveRoomStateHolder(clientProvider, viewModelScope)
    private val _searchQuery = MutableStateFlow("")
    val searchQuery: StateFlow<String> = _searchQuery.asStateFlow()
    val snackbarDispatcher = SnackbarDispatcher()
    val snackbarMessage = snackbarDispatcher.snackbarMessage.stateIn(
        scope = viewModelScope,
        started = SharingStarted.WhileSubscribed(5_000),
        initialValue = null
    )
    private val _roomListState = MutableStateFlow(
        RoomListState(

        )
    )
    val roomListState: StateFlow<RoomListState> = _roomListState.asStateFlow()

    init {
        initializeSync()
    }

    val syncState: StateFlow<SyncState> = clientProvider.getClient().syncState
    private val userAvatar: Flow<ByteArray?> =
        clientProvider.getClient().avatarUrl.map { avatarUrlOrNull ->
            avatarUrlOrNull?.let { avatarUrl ->
                clientProvider.getClient().media.getThumbnail(
                    uri = avatarUrl, height = avatarSize().toLong(), width = avatarSize().toLong()
                ).fold(onSuccess = { it.toByteArray() }, onFailure = { null })
            }

        }
    private val displayName: StateFlow<String?> = clientProvider.getClient().displayName
    val avatarData: StateFlow<AvatarData?> = combine(userAvatar, displayName) { avatar, name ->
        avatar?.let {
            AvatarData(
                id = "",
                name = name,
                url = null,
                size = AvatarSize.RoomListItem
            )
        }
    }.stateIn(viewModelScope, SharingStarted.WhileSubscribed(5_000), null)


    @OptIn(ExperimentalCoroutinesApi::class)
    val rooms: StateFlow<List<Room>> = clientProvider.getClient().room.getAll()
        .let { roomMapFlow: Flow<Map<RoomId, Flow<Room?>>> ->
            roomMapFlow.flatMapLatest { roomMap ->
                val roomFlows = roomMap.values.map { it.filterNotNull() }
                if (roomFlows.isEmpty()) {
                    flowOf(emptyList())
                } else {
                    combine(roomFlows) { it.toList() }
                }
            }
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = emptyList()
        )

    private fun initializeSync() = viewModelScope.launch {
        clientProvider.getClient().startSync()
    }


    fun search(query: String) {
        _searchQuery.value = query
    }
}



