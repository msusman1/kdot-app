/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.features.leaveroom

import net.folivo.trixnity.core.model.RoomId


data class LeaveRoomState(
    val confirmation: Confirmation,
    val progress: Progress,
    val error: Error,
) {
    sealed interface Confirmation {
        data object Hidden : Confirmation
        data class Dm(val roomId: RoomId) : Confirmation
        data class Generic(val roomId: RoomId) : Confirmation
        data class PrivateRoom(val roomId: RoomId) : Confirmation
        data class LastUserInRoom(val roomId: RoomId) : Confirmation
    }

    sealed interface Progress {
        data object Hidden : Progress
        data object Shown : Progress
    }

    sealed interface Error {
        data object Hidden : Error
        data object Shown : Error
    }
}
