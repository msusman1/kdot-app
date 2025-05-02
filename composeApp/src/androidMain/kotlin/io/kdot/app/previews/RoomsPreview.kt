package io.kdot.app.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kdot.app.ui.rooms.RoomsScreenInternal
import io.kdot.app.ui.rooms.filter.RoomFilterStateHolder
import net.folivo.trixnity.clientserverapi.client.SyncState

@Composable
@Preview(showBackground = true)
fun RoomsPreview() {
    RoomsScreenInternal(
        rooms = emptyList(),
        syncState = SyncState.INITIAL_SYNC,
        avatarData = null,
        onQueryChange = {},
        query = "",
        roomFilterStateHolder = RoomFilterStateHolder()
    )
}