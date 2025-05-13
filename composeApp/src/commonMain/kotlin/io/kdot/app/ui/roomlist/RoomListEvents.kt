/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.ui.roomlist

import io.kdot.app.domain.RoomListRoomSummary
import net.folivo.trixnity.core.model.RoomId

sealed interface RoomListEvents {
    data class UpdateVisibleRange(val range: IntRange) : RoomListEvents
    data object ToggleSearchResults : RoomListEvents
    data class AcceptInvite(val roomListRoomSummary: RoomListRoomSummary) : RoomListEvents
    data class DeclineInvite(val roomListRoomSummary: RoomListRoomSummary) : RoomListEvents
    data class ShowContextMenu(val roomListRoomSummary: RoomListRoomSummary) : RoomListEvents

    sealed interface ContextMenuEvents : RoomListEvents
    data object HideContextMenu : ContextMenuEvents
    data class LeaveRoom(val roomId: RoomId) : ContextMenuEvents
    data class MarkAsRead(val roomId: RoomId) : ContextMenuEvents
    data class MarkAsUnread(val roomId: RoomId) : ContextMenuEvents
    data class SetRoomIsFavorite(val roomId: RoomId, val isFavorite: Boolean) : ContextMenuEvents
}
