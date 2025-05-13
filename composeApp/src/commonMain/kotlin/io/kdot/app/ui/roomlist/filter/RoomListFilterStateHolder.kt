package io.kdot.app.ui.roomlist.filter

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias RoomListFilterState = Set<FilterSelectionState>

class RoomListFilterStateHolder {
    private val _selectedFilters = LinkedHashSet<RoomListFilter>()
    private val _filterSelectionState = MutableStateFlow(buildFilters())
    val filterSelectionState: StateFlow<RoomListFilterState> =
        _filterSelectionState.asStateFlow()

    val hasAnyFilterSelected = _selectedFilters.isNotEmpty()

    val selectedFilter: List<RoomListFilter> get() = _selectedFilters.toList()

    private fun select(roomListFilter: RoomListFilter) {
        _selectedFilters.add(roomListFilter)
        _filterSelectionState.value = buildFilters()
    }

    private fun deselect(roomListFilter: RoomListFilter) {
        _selectedFilters.remove(roomListFilter)
        _filterSelectionState.value = buildFilters()
    }

    private fun isSelected(roomListFilter: RoomListFilter): Boolean {
        return _selectedFilters.contains(roomListFilter)
    }

    fun handleEvent(roomListFilterEvents: RoomListFilterEvents) {
        when (roomListFilterEvents) {
            is RoomListFilterEvents.Toggle -> toggle(roomListFilterEvents.roomListFilter)
            is RoomListFilterEvents.Clear -> clear()
        }
    }

    private fun toggle(roomListFilter: RoomListFilter) {
        if (isSelected(roomListFilter)) {
            deselect(roomListFilter)
        } else {
            select(roomListFilter)
        }
    }

    private fun clear() {
        _selectedFilters.clear()
        _filterSelectionState.value = buildFilters()
    }

    private fun buildFilters(): Set<FilterSelectionState> {
        val selectedFilterStates = _selectedFilters.map {
            FilterSelectionState(
                roomListFilter = it,
                isSelected = true
            )
        }
        val unselectedFilters = RoomListFilter.entries - _selectedFilters - _selectedFilters
            .flatMap { it.incompatibleFilters }
            .toSet()
        val unselectedFilterStates = unselectedFilters.map {
            FilterSelectionState(
                roomListFilter = it,
                isSelected = false
            )
        }
        return (selectedFilterStates + unselectedFilterStates).toSet()

    }
}
