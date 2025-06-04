package io.kdot.app.ui.roomlist.filter

import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals


class RoomListFilterStateHolderTest {
    val roomListFilterStateHolder = RoomListFilterStateHolder()
    val allUnSelectedStateSet =
        RoomListFilter.entries.map(RoomListFilter::toUnSelectedState).toSet()

    @Test
    fun `getFilterSelectionState  Initial state verification`() = runTest {
        // Verify that the initial state of filterSelectionState reflects all RoomListFilter entries as unselected.
        val actual = roomListFilterStateHolder.filterSelectionState.first()
        assertEquals(allUnSelectedStateSet, actual)
    }

    @Test
    fun `getFilterSelectionState  State after selecting a filter`() = runTest {
        // Verify that after a filter is selected, filterSelectionState correctly reflects the selected filter's state as true and others as appropriate.
        roomListFilterStateHolder.handleEvent(RoomListFilterEvents.Toggle(RoomListFilter.Rooms))
        val actualFiltersStates = roomListFilterStateHolder.filterSelectionState.first()

        val expectedState = setOf(
            FilterSelectionState(
                roomListFilter = RoomListFilter.Rooms,
                isSelected = true
            ),
            FilterSelectionState(
                roomListFilter = RoomListFilter.Unread,
                isSelected = false
            ),
            FilterSelectionState(
                roomListFilter = RoomListFilter.Favourites,
                isSelected = false
            ),
        )

        assertEquals(expectedState, actualFiltersStates)

    }

    @Test
    fun `getFilterSelectionState  State after deselecting a filter`() = runTest {


        roomListFilterStateHolder.handleEvent(RoomListFilterEvents.Toggle(RoomListFilter.Unread))
        // Then deselect it
        roomListFilterStateHolder.handleEvent(RoomListFilterEvents.Toggle(RoomListFilter.Unread))

        val actualFiltersStates = roomListFilterStateHolder.filterSelectionState.first()

        assertEquals(allUnSelectedStateSet, actualFiltersStates)

    }

    @Test
    fun `getFilterSelectionState  State after clearing all filters`() = runTest {
        // Verify that after clearing all filters, filterSelectionState reflects all RoomListFilter entries as unselected.
        roomListFilterStateHolder.handleEvent(RoomListFilterEvents.Toggle(RoomListFilter.Rooms))
        roomListFilterStateHolder.handleEvent(RoomListFilterEvents.Toggle(RoomListFilter.Favourites))
        // Clear them
        roomListFilterStateHolder.handleEvent(RoomListFilterEvents.Clear)

        val actualFiltersStates: Set<FilterSelectionState> =
            roomListFilterStateHolder.filterSelectionState.first()
        val expected: Set<FilterSelectionState> =
            RoomListFilter.entries.map(RoomListFilter::toUnSelectedState).toSet()
        assertEquals(expected, actualFiltersStates)
    }

    @Test
    fun `getFilterSelectionState  test all possible combinations of filters`() = runTest {
        // Verify that multiple collectors of filterSelectionState receive the same, correct state updates.
        val firstCombination = emptyList<RoomListFilter>() to
                RoomListFilter.entries.map(RoomListFilter::toUnSelectedState)

        val secondCombination =
            listOf(RoomListFilter.Rooms) to listOf(
                FilterSelectionState(
                    roomListFilter = RoomListFilter.Rooms,
                    isSelected = true
                ),
                FilterSelectionState(
                    roomListFilter = RoomListFilter.Unread,
                    isSelected = false
                ),
                FilterSelectionState(
                    roomListFilter = RoomListFilter.Favourites,
                    isSelected = false
                ),
            )

        val thirdCombination =
            listOf(RoomListFilter.Unread) to listOf(
                FilterSelectionState(
                    roomListFilter = RoomListFilter.Unread,
                    isSelected = true
                ),
                FilterSelectionState(
                    roomListFilter = RoomListFilter.People,
                    isSelected = false
                ),
                FilterSelectionState(
                    roomListFilter = RoomListFilter.Rooms,
                    isSelected = false
                ),
                FilterSelectionState(
                    roomListFilter = RoomListFilter.Favourites,
                    isSelected = false
                ),
            )
        val FourthCombination =
            listOf(RoomListFilter.Invites) to listOf(
                FilterSelectionState(
                    roomListFilter = RoomListFilter.Invites,
                    isSelected = true
                ),
            )


        val combinations = listOf(
            firstCombination,
            secondCombination,
            thirdCombination,
            FourthCombination
        )

        combinations.forEach { (selectableEvents, res) ->
            selectableEvents.forEach {
                roomListFilterStateHolder.handleEvent(RoomListFilterEvents.Toggle(it))
            }
            val actualFiltersStates = roomListFilterStateHolder.filterSelectionState.first()
            val expected = res.toSet()
            assertEquals(expected, actualFiltersStates)
            roomListFilterStateHolder.handleEvent(RoomListFilterEvents.Clear)
        }
    }

}