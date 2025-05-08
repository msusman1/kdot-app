package io.kdot.app.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kdot.app.ui.roomlist.RoomListScreenInternal
import io.kdot.app.ui.roomlist.filter.RoomListFilterStateHolder
import net.folivo.trixnity.clientserverapi.client.SyncState

@Composable
@Preview(showBackground = true)
fun RoomsPreview() {
    RoomListScreenInternal(
        rooms = emptyList(),
        syncState = SyncState.INITIAL_SYNC,
        avatarData = null,
        onQueryChange = {},
        query = "",
        roomListFilterStateHolder = RoomListFilterStateHolder()
    )
}