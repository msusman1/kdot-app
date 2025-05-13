package io.kdot.app.previews.roomlist

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import io.kdot.app.previews.KDotPreview
import io.kdot.app.previews.PreviewDayNight
import io.kdot.app.ui.roomlist.MatrixUser
import io.kdot.app.ui.roomlist.components.RoomListTopBar
import io.kdot.app.ui.roomlist.filter.FilterSelectionState
import io.kdot.app.ui.roomlist.filter.RoomListFilter
import net.folivo.trixnity.core.model.UserId


fun aRoomListFiltersState(
    filterSelectionStates: List<FilterSelectionState> = RoomListFilter.entries.map {
        FilterSelectionState(
            it,
            isSelected = false
        )
    },
) = filterSelectionStates.toSet()


@OptIn(ExperimentalMaterial3Api::class)
@PreviewDayNight
@Composable
private fun RoomListTopBarUnSelectedPreview() {
    KDotPreview {
        RoomListTopBar(
            matrixUser = MatrixUser(UserId("@id:domain"), "Alice"),
            showAvatarIndicator = true,
            onToggleSearch = {},
            onOpenSettings = {},
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            displayFilters = true,
            roomListFilterState = aRoomListFiltersState(),
            eventSinkRoomListFilter = {})
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@PreviewDayNight
@Composable
private fun RoomListTopBarSelectedPreview() {
    val roomListFilter =
        RoomListFilter.entries.mapIndexed { index, roomListFilter ->
            FilterSelectionState(
                roomListFilter,
                isSelected = index == 0
            )
        }
    KDotPreview {
        RoomListTopBar(
            matrixUser = MatrixUser(UserId("@id:domain"), "Alice"),
            showAvatarIndicator = false,
            onToggleSearch = {},
            onOpenSettings = {},
            scrollBehavior = TopAppBarDefaults.enterAlwaysScrollBehavior(),
            displayFilters = true,
            roomListFilterState = aRoomListFiltersState(roomListFilter),
            eventSinkRoomListFilter = {})
    }
}
