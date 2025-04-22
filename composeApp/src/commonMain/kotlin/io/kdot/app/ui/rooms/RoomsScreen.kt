package io.kdot.app.ui.rooms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Divider
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.compose.rememberAsyncImagePainter
import net.folivo.trixnity.clientserverapi.client.SyncState
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomsScreen(
    viewModel: RoomsViewModel = koinViewModel()
) {
    val rooms by viewModel.rooms.collectAsState()
    val syncState by viewModel.syncState.collectAsState()
    val userAvatar by viewModel.userAvatar.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Chats") },
                navigationIcon = {
                    AsyncImage(
                        model = userAvatar,
                        contentDescription = null,
                        modifier = Modifier
                            .size(48.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                },
                actions = {
                    IconButton(onClick = { /* Search action */ }) {
                        Icon(Icons.Default.Search, contentDescription = "Search")
                    }
                }
            )
        }
    ) { padding ->
        Column(modifier = Modifier.padding(padding)) {

            // Show sync state banner
            when (syncState) {
                SyncState.INITIAL_SYNC -> SyncBanner("Initial sync in progress...")
                SyncState.STARTED -> SyncBanner("Sync started...")
                SyncState.RUNNING -> {} // Optionally hide
                SyncState.TIMEOUT -> SyncBanner("Sync timed out", isError = true)
                SyncState.ERROR -> SyncBanner("Sync failed", isError = true)
                SyncState.STOPPED -> SyncBanner("Sync stopped", isError = true)
            }

            LazyColumn {
                items(rooms, key = { it.roomId.full }) { room ->
                    RoomItem(room = room)
                    Divider()
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