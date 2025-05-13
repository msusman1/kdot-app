package io.kdot.app.ui.roomlist.search

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class RoomListSearchStateHolder {
    private val _state = MutableStateFlow(
        RoomListSearchState(
            isSearchActive = false,
            query = "",
            results = emptyList(),
            isRoomDirectorySearchEnabled = false
        )
    )
    val state = _state.asStateFlow()

    fun handleEvent(event: RoomListSearchEvents) {
        when (event) {
            RoomListSearchEvents.ClearQuery -> {
                _state.update {
                    it.copy(query = "")
                }
            }

            is RoomListSearchEvents.QueryChanged -> {
                _state.update {
                    it.copy(query = event.query)
                }
            }

            RoomListSearchEvents.ToggleSearchVisibility -> {
                _state.update {
                    it.copy(isSearchActive = !it.isSearchActive)
                }
            }
        }
    }
}