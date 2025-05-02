package io.kdot.app.ui.rooms

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text.input.TextFieldState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import io.kdot.app.domain.AvatarData
import io.kdot.app.ui.rooms.filter.RoomFilterStateHolder
import io.kdot.app.ui.rooms.filter.RoomFilterView
import net.folivo.trixnity.client.store.Room
import net.folivo.trixnity.clientserverapi.client.SyncState
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RoomsScreen(
    viewModel: RoomsViewModel = koinViewModel()
) {
    val rooms by viewModel.rooms.collectAsState()
    val syncState by viewModel.syncState.collectAsState()
    val avatarData by viewModel.avatarData.collectAsState()
    val searchQuery by viewModel.searchQuery.collectAsState()

    RoomsScreenInternal(
        roomFilterStateHolder = viewModel.roomFilterStateHolder,
        rooms = rooms,
        syncState = syncState,
        avatarData = avatarData,
        query = searchQuery,
        onQueryChange = viewModel::search
    )
}

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun RoomsScreenInternal(
    roomFilterStateHolder: RoomFilterStateHolder,
    rooms: List<Room>,
    syncState: SyncState,
    avatarData: AvatarData?,
    query: String,
    onQueryChange: (String) -> Unit,
) {
    Scaffold(
        topBar = {
            RoomsTopAppBar(avatarData)
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {
            when (syncState) {
                SyncState.INITIAL_SYNC -> SyncBanner("Initial sync in progress...")
                SyncState.STARTED -> SyncBanner("Sync started...")
                SyncState.RUNNING -> {} // Optionally hide
                SyncState.TIMEOUT -> SyncBanner("Sync timed out", isError = true)
                SyncState.ERROR -> SyncBanner("Sync failed", isError = true)
                SyncState.STOPPED -> SyncBanner("Sync stopped", isError = true)
            }


            LazyColumn {
                item {
                    RoomsSearchBar(
                        query = query,
                        onQueryChange = onQueryChange,
                        modifier = Modifier.padding(16.dp)
                    )
                }
                stickyHeader {
                    RoomFilterView(roomFilterStateHolder)
                }
                items(rooms, key = { it.roomId.full }) { room ->
                    RoomItem(room = room)
                }
            }
        }

    }
}

@Composable
fun SyncBanner(message: String, isError: Boolean = false) {
    val bgColor =
        if (isError) MaterialTheme.colorScheme.errorContainer else MaterialTheme.colorScheme.primaryContainer
    val textColor =
        if (isError) MaterialTheme.colorScheme.onErrorContainer else MaterialTheme.colorScheme.onPrimaryContainer

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .background(bgColor)
            .padding(8.dp)
    ) {
        Text(
            text = message,
            color = textColor,
            style = MaterialTheme.typography.bodySmall,
            modifier = Modifier.align(Alignment.CenterStart)
        )
    }
}