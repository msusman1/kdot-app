package io.kdot.app.ui.roomlist.filter

import androidx.compose.material.icons.Icons
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class RoomListFilterStateHolder {
    private val _selectedFilters = LinkedHashSet<RoomListFilter>()
    private val _filterSelectionStates = MutableStateFlow(buildFilters())
    val filterSelectionStates: StateFlow<Set<FilterSelectionState>> =
        _filterSelectionStates.asStateFlow()

    val hasAnyFilterSelected = _selectedFilters.isNotEmpty()

    val  selectedFilter:List<RoomListFilter> get() = _selectedFilters.toList()

    private fun select(roomListFilter: RoomListFilter) {
        _selectedFilters.add(roomListFilter)
        _filterSelectionStates.value = buildFilters()
    }

    private fun deselect(roomListFilter: RoomListFilter) {
        _selectedFilters.remove(roomListFilter)
        _filterSelectionStates.value = buildFilters()
    }

    private fun isSelected(roomListFilter: RoomListFilter): Boolean {
        return _selectedFilters.contains(roomListFilter)
    }

    fun toggle(roomListFilter: RoomListFilter) {
        if (isSelected(roomListFilter)) {
            deselect(roomListFilter)
        } else {
            select(roomListFilter)
        }
    }

    fun clear() {
        _selectedFilters.clear()
        _filterSelectionStates.value = buildFilters()
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
