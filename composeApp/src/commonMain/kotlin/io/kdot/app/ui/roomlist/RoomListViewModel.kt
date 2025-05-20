package io.kdot.app.ui.roomlist

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import io.element.android.features.roomlist.impl.model.RoomSummaryDisplayType
import io.github.aakira.napier.Napier
import io.kdot.app.data.RoomListRoomSummaryFactory
import io.kdot.app.designsystem.utils.snackbar.SnackbarDispatcher
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.features.leaveroom.LeaveRoomEvent
import io.kdot.app.features.leaveroom.LeaveRoomStateHolder
import io.kdot.app.libraries.core.data.combine
import io.kdot.app.matrix.MatrixClientProvider
import io.kdot.app.matrix.extensions.getAllFlatten
import io.kdot.app.ui.roomlist.RoomListState.ContextMenu
import io.kdot.app.ui.roomlist.filter.RoomListFilter
import io.kdot.app.ui.roomlist.filter.RoomListFilterEvents
import io.kdot.app.ui.roomlist.filter.RoomListFilterStateHolder
import io.kdot.app.ui.roomlist.search.RoomListSearchEvents
import io.kdot.app.ui.roomlist.search.RoomListSearchStateHolder
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import net.folivo.trixnity.client.room
import net.folivo.trixnity.client.store.Room


class RoomListViewModel(
    private val clientProvider: MatrixClientProvider,
    private val roomListRoomSummaryFactory: RoomListRoomSummaryFactory
) : ViewModel() {
    private val roomListFilterStateHolder = RoomListFilterStateHolder()
    private val leaveRoomStateHolder = LeaveRoomStateHolder(clientProvider, viewModelScope)
    private val roomListSearchStateHolder = RoomListSearchStateHolder()
    private val snackbarDispatcher = SnackbarDispatcher()

    private val matrixUser = createMatrixUserFlow()
    private val roomsFlow: Flow<List<RoomListRoomSummary>> = getRoomListFlow()
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
        val roomsFlow: Flow<List<Room>> = client.room.getAllFlatten().onEach {
            Napier.d("flow size: ${it.size}")
        }
        return roomsFlow.flatMapLatest { rooms ->
            if (rooms.isEmpty()) {
                flowOf(emptyList())
            } else {
                val summaryFlows = rooms.map { room ->
                    roomListRoomSummaryFactory.create(client, room)
                }
                combine(summaryFlows) { it.toList() }
            }
        }.onEach {
            Napier.d("Summary size: ${it.size}")
        }
    }


    private fun combineRoomListState(): StateFlow<RoomListState> {
        return combine(
            matrixUser,
            roomListFilterStateHolder.filterSelectionState,
            snackbarDispatcher.snackbarMessage,
            leaveRoomStateHolder.leaveRoomStateFlow,
            roomListSearchStateHolder.state,
            roomsFlow
        ) { user, filterState, snackbarMessage, leaveRoomState, searchState, rooms ->



            val selectedFilters = filterState
                .filter { it.isSelected }
                .map { it.roomListFilter }
            val filteredResults = selectedFilters.fold(rooms) { filtered, filter ->
                when (filter) {
                    RoomListFilter.Unread -> filtered.filter { it.hasNewContent }
                    RoomListFilter.People -> filtered.filter { it.isDm }
                    RoomListFilter.Rooms -> filtered.filter { !it.isDm }
                    RoomListFilter.Favourites -> filtered.filter { it.isFavorite }
                    RoomListFilter.Invites -> filtered.filter { it.displayType == RoomSummaryDisplayType.INVITE }
                }
            }

            val roomContentState = if (filteredResults.isEmpty()) RoomListContentState.Empty
            else RoomListContentState.Rooms(filteredResults.toList())


            val searchResults = if (searchState.isSearchActive) {
                filteredResults.filter { it.name?.contains(searchState.query, true) == true }
            } else {
                emptyList<RoomListRoomSummary>()
            }

            RoomListState(
                matrixUser = user,
                showAvatarIndicator = true,
                hasNetworkConnection = true,
                contextMenu = ContextMenu.Hidden,
                contentState = roomContentState,
                leaveRoomState = leaveRoomState,
                snackbarMessage = snackbarMessage,
                filterState = filterState,
                searchState = searchState.copy(results = searchResults)
            )
        }.flowOn(Dispatchers.Default)
            .stateIn(
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


    fun eventSink(contextMenuEvents: RoomListEvents) {
        when (contextMenuEvents) {
            is RoomListEvents.AcceptInvite -> {}
            RoomListEvents.HideContextMenu -> {}
            is RoomListEvents.LeaveRoom -> {}
            is RoomListEvents.MarkAsRead -> {}
            is RoomListEvents.MarkAsUnread -> {}
            is RoomListEvents.SetRoomIsFavorite -> {}
            is RoomListEvents.DeclineInvite -> {}
            is RoomListEvents.ShowContextMenu -> {}
            RoomListEvents.ToggleSearchResults -> {
                roomListSearchStateHolder.handleEvent(RoomListSearchEvents.ToggleSearchVisibility)
            }

            is RoomListEvents.UpdateVisibleRange -> {}
        }
    }

    fun eventSinkLeaveRoom(leaveRoomEvent: LeaveRoomEvent) {
        leaveRoomStateHolder.handleEvent(leaveRoomEvent)
    }

    fun eventSinkSearch(roomListSearchEvents: RoomListSearchEvents) {
        roomListSearchStateHolder.handleEvent(roomListSearchEvents)
    }

    fun eventSinkFilter(roomListFilterEvents: RoomListFilterEvents) {
        roomListFilterStateHolder.handleEvent(roomListFilterEvents)
    }
}



