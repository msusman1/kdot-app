package io.kdot.app.ui.roomlist.filter

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow

typealias RoomListFilterState = Set<FilterSelectionState>

class RoomListFilterStateHolder {
    private val _selectedFilters = LinkedHashSet<RoomListFilter>()
    private val _filterSelectionState = MutableStateFlow(buildFilters())
    val filterSelectionState = _filterSelectionState.asStateFlow()


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
        val selectedFilterStates = _selectedFilters.map(RoomListFilter::toSelectedState)

        val incompatibleFilters = _selectedFilters
            .flatMap { it.incompatibleFilters }
            .toSet()
        val allFilters = RoomListFilter.entries
        val remainingUnselectedFilters = allFilters - incompatibleFilters - _selectedFilters
        val unselectedFilterStates =
            remainingUnselectedFilters.map(RoomListFilter::toUnSelectedState)
        return (selectedFilterStates + unselectedFilterStates).toSet()

    }
}
