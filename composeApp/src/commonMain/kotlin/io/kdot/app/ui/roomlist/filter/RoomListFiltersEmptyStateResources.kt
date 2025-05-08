/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.ui.roomlist.filter

import io.kdot.app.designsystem.Resources
import org.jetbrains.compose.resources.StringResource

/**
 * Holds the resources for the empty state when filters are applied to the room list.
 * @param title the title of the empty state
 * @param subtitle the subtitle of the empty state
 */
data class RoomListFiltersEmptyStateResources(
    val title: StringResource,
    val subtitle: StringResource,
) {
    companion object {
        /**
         * Create a [RoomListFiltersEmptyStateResources] from a list of selected filters.
         */
        fun fromSelectedFilters(selectedFilters: List<RoomListFilter>): RoomListFiltersEmptyStateResources? {
            return when {
                selectedFilters.isEmpty() -> null
                selectedFilters.size == 1 -> {
                    when (selectedFilters.first()) {
                        RoomListFilter.Unread -> RoomListFiltersEmptyStateResources(
                            title = Resources.String.screen_roomlist_filter_unreads_empty_state_title,
                            subtitle =Resources.String.screen_roomlist_filter_mixed_empty_state_subtitle
                        )
                        RoomListFilter.People -> RoomListFiltersEmptyStateResources(
                            title = Resources.String.screen_roomlist_filter_people_empty_state_title,
                            subtitle = Resources.String.screen_roomlist_filter_mixed_empty_state_subtitle
                        )
                        RoomListFilter.Rooms -> RoomListFiltersEmptyStateResources(
                            title = Resources.String.screen_roomlist_filter_rooms_empty_state_title,
                            subtitle = Resources.String.screen_roomlist_filter_mixed_empty_state_subtitle
                        )
                        RoomListFilter.Favourites -> RoomListFiltersEmptyStateResources(
                            title = Resources.String.screen_roomlist_filter_favourites_empty_state_title,
                            subtitle = Resources.String.screen_roomlist_filter_favourites_empty_state_subtitle
                        )
                        RoomListFilter.Invites -> RoomListFiltersEmptyStateResources(
                            title = Resources.String.screen_roomlist_filter_invites_empty_state_title,
                            subtitle = Resources.String.screen_roomlist_filter_mixed_empty_state_subtitle
                        )
                    }
                }
                else -> RoomListFiltersEmptyStateResources(
                    title = Resources.String.screen_roomlist_filter_mixed_empty_state_title,
                    subtitle = Resources.String.screen_roomlist_filter_mixed_empty_state_subtitle
                )
            }
        }
    }
}
