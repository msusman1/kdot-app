/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.ui.roomlist.search

import io.kdot.app.domain.RoomListRoomSummary

data class RoomListSearchState(
    val isSearchActive: Boolean,
    val query: String,
    val results: List<RoomListRoomSummary>,
    val isRoomDirectorySearchEnabled: Boolean,
    val eventSink: (RoomListSearchEvents) -> Unit
) {
    val displayRoomDirectorySearch = query.isEmpty() && isRoomDirectorySearchEnabled
}
