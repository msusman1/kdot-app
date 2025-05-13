package io.kdot.app.ui.roomlist

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.Chat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.utils.snackbar.SnackbarHost
import io.kdot.app.designsystem.utils.snackbar.SnackbarMessage
import io.kdot.app.designsystem.utils.snackbar.rememberSnackbarHostState
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.features.leaveroom.LeaveRoomEvent
import io.kdot.app.features.leaveroom.LeaveRoomState
import io.kdot.app.features.leaveroom.LeaveRoomView
import io.kdot.app.features.networkmonitor.ConnectivityIndicatorContainer
import io.kdot.app.ui.roomlist.components.RoomListTopBar
import io.kdot.app.ui.roomlist.filter.RoomListFilterEvents
import io.kdot.app.ui.roomlist.filter.RoomListFilterState
import io.kdot.app.ui.roomlist.search.RoomListSearchEvents
import io.kdot.app.ui.roomlist.search.RoomListSearchView
import io.kdot.app.ui.theme.appColors
import net.folivo.trixnity.core.model.RoomId
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoomListScreen(
    viewModel: RoomListViewModel = koinViewModel(),
    onRoomClick: (RoomId) -> Unit,
    onSettingsClick: () -> Unit,
    onCreateRoomClick: () -> Unit,
    onRoomSettingsClick: (roomId: RoomId) -> Unit,
    onRoomDirectorySearchClick: () -> Unit,
) {
    val roomListState by viewModel.roomListState.collectAsStateWithLifecycle()

    RoomListScreenInternal(
        state = roomListState,
        snackBarMessage = roomListState.snackbarMessage,
        roomListFilterState = roomListState.filterState,
        leaveRoomState = roomListState.leaveRoomState,
        onRoomClick = onRoomClick,
        onSettingsClick = onSettingsClick,
        onCreateRoomClick = onCreateRoomClick,
        onRoomSettingsClick = onRoomSettingsClick,
        onRoomDirectorySearchClick = onRoomDirectorySearchClick,
        eventSinkContextMenu = viewModel::eventSink,
        eventSinkLeaveRoom = viewModel::eventSinkLeaveRoom,
        eventSinkRoomList = viewModel::eventSink,
        eventSinkRoomListSearch = viewModel::eventSinkSearch,
        eventSinkRoomListFilter = viewModel::eventSinkFilter,
    )
}

@Composable
fun RoomListScreenInternal(
    state: RoomListState,
    eventSinkContextMenu: (RoomListEvents.ContextMenuEvents) -> Unit,
    eventSinkLeaveRoom: (LeaveRoomEvent) -> Unit,
    eventSinkRoomList: (RoomListEvents) -> Unit,
    eventSinkRoomListSearch: (RoomListSearchEvents) -> Unit,
    eventSinkRoomListFilter: (RoomListFilterEvents) -> Unit,
    snackBarMessage: SnackbarMessage?,
    roomListFilterState: RoomListFilterState,
    leaveRoomState: LeaveRoomState,
    onRoomClick: (RoomId) -> Unit,
    onSettingsClick: () -> Unit,
    onCreateRoomClick: () -> Unit,
    onRoomSettingsClick: (roomId: RoomId) -> Unit,
    onRoomDirectorySearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ConnectivityIndicatorContainer(
        modifier = modifier,
        isOnline = state.hasNetworkConnection,
    ) { topPadding ->
        Box {
            if (state.contextMenu is RoomListState.ContextMenu.Shown) {
                RoomListContextMenu(
                    contextMenu = state.contextMenu,
                    eventSink = eventSinkContextMenu,
                    onRoomSettingsClick = onRoomSettingsClick,
                )
            }

            LeaveRoomView(
                leaveRoomState = leaveRoomState,
                eventSink = eventSinkLeaveRoom
            )

            RoomListScaffold(
                state = state,
                snackBarMessage = snackBarMessage,
                roomListFilterState = roomListFilterState,
                onRoomClick = onRoomClick,
                onOpenSettings = onSettingsClick,
                onCreateRoomClick = onCreateRoomClick,
                eventSinkRoomList = eventSinkRoomList,
                eventSinkRoomListFilter = eventSinkRoomListFilter,
                modifier = Modifier.padding(top = topPadding),
            )
            // This overlaid view will only be visible when state.displaySearchResults is true
            RoomListSearchView(
                state = state.searchState,
                eventSinkRoomList = eventSinkRoomList,
                eventSinkRoomListSearch = eventSinkRoomListSearch,
                onRoomClick = onRoomClick,
                onRoomDirectorySearchClick = onRoomDirectorySearchClick,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = topPadding)
                    .fillMaxSize()
                    .background(MaterialTheme.appColors.bgCanvasDefault)
            )

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoomListScaffold(
    state: RoomListState,
    roomListFilterState: RoomListFilterState,
    snackBarMessage: SnackbarMessage?,
    onRoomClick: (RoomId) -> Unit,
    onOpenSettings: () -> Unit,
    eventSinkRoomList: (RoomListEvents) -> Unit,
    eventSinkRoomListFilter: (RoomListFilterEvents) -> Unit,
    onCreateRoomClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    fun onRoomClick(room: RoomListRoomSummary) {
        onRoomClick(room.roomId)
    }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)
    val snackBarHostState = rememberSnackbarHostState(snackbarMessage = snackBarMessage)

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RoomListTopBar(
                matrixUser = state.matrixUser,
                showAvatarIndicator = state.showAvatarIndicator,
                onToggleSearch = { eventSinkRoomList(RoomListEvents.ToggleSearchResults) },
                onOpenSettings = onOpenSettings,
                scrollBehavior = scrollBehavior,
                displayFilters = state.displayFilters,
                roomListFilterState = roomListFilterState,
                eventSinkRoomListFilter = eventSinkRoomListFilter
            )
        },
        content = { padding ->
            RoomListContentView(
                contentState = state.contentState,
                filtersState = roomListFilterState,
                eventSinkRoomList = eventSinkRoomList,
                onRoomClick = ::onRoomClick,
                onCreateRoomClick = onCreateRoomClick,
                modifier = Modifier
                    .padding(padding)
                    .consumeWindowInsets(padding)
            )
        },
        floatingActionButton = {
            FloatingActionButton(
                containerColor = MaterialTheme.appColors.iconPrimary,
                onClick = onCreateRoomClick
            ) {
                Icon(
                    // Note cannot use Icons.Outlined.EditSquare, it does not exist :/
                    imageVector = Icons.AutoMirrored.Filled.Chat,
                    contentDescription = stringResource(Resources.String.screen_roomlist_a11y_create_message),
                    tint = MaterialTheme.appColors.iconOnSolidPrimary,
                )
            }
        },
        snackbarHost = { SnackbarHost(snackBarHostState) },
    )
}

