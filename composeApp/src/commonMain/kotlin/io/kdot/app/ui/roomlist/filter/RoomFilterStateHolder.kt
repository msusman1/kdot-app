package io.kdot.app.ui.roomlist.filter

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow


class RoomFilterStateHolder {
    private val selectedFilters = LinkedHashSet<RoomFilter>()
    private val _filterSelectionStates = MutableStateFlow(buildFilters())
    val filterSelectionStates: StateFlow<Set<FilterSelectionState>> =
        _filterSelectionStates.asStateFlow()

    private fun select(roomFilter: RoomFilter) {
        selectedFilters.add(roomFilter)
        _filterSelectionStates.value = buildFilters()
    }

    private fun deselect(roomFilter: RoomFilter) {
        selectedFilters.remove(roomFilter)
        _filterSelectionStates.value = buildFilters()
    }

    private fun isSelected(roomFilter: RoomFilter): Boolean {
        return selectedFilters.contains(roomFilter)
    }

    fun toggle(roomFilter: RoomFilter) {
        if (isSelected(roomFilter)) {
            deselect(roomFilter)
        } else {
            select(roomFilter)
        }
    }

    fun clear() {
        selectedFilters.clear()
        _filterSelectionStates.value = buildFilters()
    }

    private fun buildFilters(): Set<FilterSelectionState> {
        val selectedFilterStates = selectedFilters.map {
            FilterSelectionState(
                roomFilter = it,
                isSelected = true
            )
        }
        val unselectedFilters = RoomFilter.entries - selectedFilters - selectedFilters
            .flatMap { it.incompatibleFilters }
            .toSet()
        val unselectedFilterStates = unselectedFilters.map {
            FilterSelectionState(
                roomFilter = it,
                isSelected = false
            )
        }
        return (selectedFilterStates + unselectedFilterStates).toSet()

    }
}
