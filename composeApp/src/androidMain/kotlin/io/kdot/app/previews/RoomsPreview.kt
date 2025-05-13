package io.kdot.app.previews

import androidx.compose.runtime.Composable
import androidx.compose.ui.tooling.preview.Preview
import io.kdot.app.features.leaveroom.LeaveRoomState
import io.kdot.app.ui.roomlist.RoomListScreenInternal
import io.kdot.app.ui.roomlist.RoomListState

@Composable
@Preview(showBackground = true)
fun RoomsPreview() {
    RoomListScreenInternal(
        state = RoomListState.Default,
        eventSinkContextMenu = {},
        eventSinkLeaveRoom = {},
        eventSinkRoomList = {},
        eventSinkRoomListSearch = {},
        eventSinkRoomListFilter = {},
        snackBarMessage = null,
        roomListFilterState = emptySet(),
        leaveRoomState = LeaveRoomState.Default,
        onRoomClick = {},
        onSettingsClick = {},
        onCreateRoomClick = {},
        onRoomSettingsClick = {},
        onRoomDirectorySearchClick = {},

        )
}