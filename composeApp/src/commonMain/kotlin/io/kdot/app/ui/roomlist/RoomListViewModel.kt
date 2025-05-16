package io.kdot.app.ui.roomlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.kdot.app.designsystem.utils.snackbar.SnackbarDispatcher
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.domain.createRoomSummary
import io.kdot.app.features.leaveroom.LeaveRoomEvent
import io.kdot.app.features.leaveroom.LeaveRoomStateHolder
import io.kdot.app.libraries.core.data.combine
import io.kdot.app.matrix.MatrixClientProvider
import io.kdot.app.ui.roomlist.RoomListState.ContextMenu
import io.kdot.app.ui.roomlist.filter.RoomListFilterEvents
import io.kdot.app.ui.roomlist.filter.RoomListFilterStateHolder
import io.kdot.app.ui.roomlist.search.RoomListSearchEvents
import io.kdot.app.ui.roomlist.search.RoomListSearchStateHolder
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


class RoomListViewModel(
    private val clientProvider: MatrixClientProvider,
) : ViewModel() {
    private val roomListFilterStateHolder = RoomListFilterStateHolder()
    private val leaveRoomStateHolder = LeaveRoomStateHolder(clientProvider, viewModelScope)
    private val roomListSearchStateHolder = RoomListSearchStateHolder()
    private val snackbarDispatcher = SnackbarDispatcher()

    private val matrixUser = createMatrixUserFlow()
    private val rooms: Flow<List<RoomListRoomSummary>> = getRoomListFlow()
    val roomListState = combineRoomListState()

    private fun createMatrixUserFlow(): Flow<MatrixUser> {
        val avatarUrl = clientProvider.getClient().avatarUrl
        val displayName = clientProvider.getClient().displayName
        return combine(avatarUrl, displayName) { aUrl, name ->
            MatrixUser(
                userId = clientProvider.getClient().userId,
                displayName = name,
                avatarUrl = aUrl
            )
        }
    }


    @OptIn(ExperimentalCoroutinesApi::class)
    private fun getRoomListFlow(): Flow<List<RoomListRoomSummary>> {
        val client = clientProvider.getClient()
        return client.room
            .getAll()
            .flatMapLatest { roomsById ->
                val summaryFlows: List<Flow<RoomListRoomSummary>> = roomsById.values
                    .map { roomFlow ->
                        roomFlow.filterNotNull()
                            .flatMapLatest { room ->
                                createRoomSummary(client, room)
                            }
                    }
                if (summaryFlows.isEmpty()) {
                    flowOf(emptyList())
                } else {
                    combine(summaryFlows) { summariesArray ->
                        summariesArray.toList()
                    }
                }
            }
    }

    private fun combineRoomListState(): StateFlow<RoomListState> {
        return combine(
            matrixUser,
            roomListFilterStateHolder.filterSelectionState,
            snackbarDispatcher.snackbarMessage,
            leaveRoomStateHolder.leaveRoomStateFlow,
            roomListSearchStateHolder.state,
            rooms
        ) { usr, filterState, snackbarMessage, leaveRoomState, searchState, rms ->
            val roomContentState = if (rms.isEmpty()) {
                RoomListContentState.Empty
            } else {
                RoomListContentState.Rooms(rms)
            }
            RoomListState(
                matrixUser = usr,
                showAvatarIndicator = true,
                hasNetworkConnection = true,
                contextMenu = ContextMenu.Hidden,
                contentState = roomContentState,
                leaveRoomState = leaveRoomState,
                snackbarMessage = snackbarMessage,
                filterState = filterState,
                searchState = searchState
            )
        }.stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000),
            initialValue = RoomListState.Default
        )
    }


    init {
        initializeSync()
    }

    private fun initializeSync() = viewModelScope.launch {
        clientProvider.getClient().startSync()
    }


    fun eventSink(contextMenuEvents: RoomListEvents) {}

    fun eventSinkLeaveRoom(leaveRoomEvent: LeaveRoomEvent) = leaveRoomStateHolder::handleEvent

    fun eventSinkSearch(roomListSearchEvents: RoomListSearchEvents) =
        roomListSearchStateHolder::handleEvent

    fun eventSinkFilter(roomListFilterEvents: RoomListFilterEvents) =
        roomListFilterStateHolder::handleEvent
}



