package io.kdot.app.ui.roomlist

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Chat
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.rememberTopAppBarState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.utils.snackbar.SnackbarHost
import io.kdot.app.designsystem.utils.snackbar.SnackbarMessage
import io.kdot.app.designsystem.utils.snackbar.rememberSnackbarHostState
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.features.leaveroom.LeaveRoomStateHolder
import io.kdot.app.features.leaveroom.LeaveRoomView
import io.kdot.app.features.networkmonitor.ConnectivityIndicatorContainer
import io.kdot.app.ui.roomlist.components.RoomListTopBar
import io.kdot.app.ui.roomlist.filter.RoomFilterStateHolder
import io.kdot.app.ui.theme.appColors
import net.folivo.trixnity.core.model.RoomId
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoomListScreen(
    viewModel: RoomListViewModel = koinViewModel(),
    onRoomClick: (RoomId) -> Unit,
    onSettingsClick: () -> Unit,
    onSetUpRecoveryClick: () -> Unit,
    onConfirmRecoveryKeyClick: () -> Unit,
    onCreateRoomClick: () -> Unit,
    onRoomSettingsClick: (roomId: RoomId) -> Unit,
    onRoomDirectorySearchClick: () -> Unit,
) {
    val roomListState by viewModel.roomListState.collectAsState()
    val roomFilterStateHolder = viewModel.roomFilterStateHolder
    val snackbarMessage by viewModel.snackbarMessage.collectAsState()
    val leaveRoomStateHolder = viewModel.leaveRoomStateHolder

    RoomListScreenInternal(
        state = roomListState,
        snackbarMessage = snackbarMessage,
        roomFilterStateHolder = roomFilterStateHolder,
        leaveRoomStateHolder = leaveRoomStateHolder,
        onRoomClick = onRoomClick,
        onSettingsClick = onSettingsClick,
        onSetUpRecoveryClick = onSetUpRecoveryClick,
        onConfirmRecoveryKeyClick = onConfirmRecoveryKeyClick,
        onCreateRoomClick = onCreateRoomClick,
        onRoomSettingsClick = onRoomSettingsClick,
        onRoomDirectorySearchClick = onRoomDirectorySearchClick,
    )
}

@Composable
fun RoomListScreenInternal(
    state: RoomListState,
    snackbarMessage: SnackbarMessage?,
    roomFilterStateHolder: RoomFilterStateHolder,
    leaveRoomStateHolder: LeaveRoomStateHolder,
    onRoomClick: (RoomId) -> Unit,
    onSettingsClick: () -> Unit,
    onSetUpRecoveryClick: () -> Unit,
    onConfirmRecoveryKeyClick: () -> Unit,
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
                    eventSink = state.eventSink,
                    onRoomSettingsClick = onRoomSettingsClick,
                )
            }

            LeaveRoomView(leaveRoomStateHolder = leaveRoomStateHolder)

            RoomListScaffold(
                state = state,
                snackbarMessage = snackbarMessage,
                roomFilterStateHolder = roomFilterStateHolder,
                onSetUpRecoveryClick = onSetUpRecoveryClick,
                onConfirmRecoveryKeyClick = onConfirmRecoveryKeyClick,
                onRoomClick = onRoomClick,
                onOpenSettings = onSettingsClick,
                onCreateRoomClick = onCreateRoomClick,
                modifier = Modifier.padding(top = topPadding),
            )
            // This overlaid view will only be visible when state.displaySearchResults is true
            RoomListSearchView(
                state = state.searchState,
                eventSink = state.eventSink,
                onRoomClick = onRoomClick,
                onRoomDirectorySearchClick = onRoomDirectorySearchClick,
                modifier = Modifier
                    .statusBarsPadding()
                    .padding(top = topPadding)
                    .fillMaxSize()
                    .background(ElementTheme.colors.bgCanvasDefault)
            )

        }

    }

}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoomListScaffold(
    state: RoomListState,
    roomFilterStateHolder: RoomFilterStateHolder,
    snackbarMessage: SnackbarMessage?,
    onSetUpRecoveryClick: () -> Unit,
    onConfirmRecoveryKeyClick: () -> Unit,
    onRoomClick: (RoomId) -> Unit,
    onOpenSettings: () -> Unit,
    onCreateRoomClick: () -> Unit,
    modifier: Modifier = Modifier,
) {
    fun onRoomClick(room: RoomListRoomSummary) {
        onRoomClick(room.roomId)
    }

    val appBarState = rememberTopAppBarState()
    val scrollBehavior = TopAppBarDefaults.exitUntilCollapsedScrollBehavior(appBarState)
    val snackbarHostState = rememberSnackbarHostState(snackbarMessage = snackbarMessage)

    Scaffold(
        modifier = modifier.nestedScroll(scrollBehavior.nestedScrollConnection),
        topBar = {
            RoomListTopBar(
                matrixUser = state.matrixUser,
                showAvatarIndicator = state.showAvatarIndicator,
                onToggleSearch = { state.eventSink(RoomListEvents.ToggleSearchResults) },
                onOpenSettings = onOpenSettings,
                scrollBehavior = scrollBehavior,
                displayFilters = state.displayFilters,
                roomFilterStateHolder = roomFilterStateHolder
            )
        },
        content = { padding ->
            RoomListContentView(
                contentState = state.contentState,
                filtersState = state.filtersState,
                eventSink = state.eventSink,
                onSetUpRecoveryClick = onSetUpRecoveryClick,
                onConfirmRecoveryKeyClick = onConfirmRecoveryKeyClick,
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
                    imageVector = Icons.Default.Chat,
                    contentDescription = stringResource(Resources.String.screen_roomlist_a11y_create_message),
                    tint = MaterialTheme.appColors.iconOnSolidPrimary,
                )
            }
        },
        snackbarHost = { SnackbarHost(snackbarHostState) },
    )
}

