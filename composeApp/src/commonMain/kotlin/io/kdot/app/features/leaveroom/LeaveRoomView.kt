/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.features.leaveroom

import androidx.compose.runtime.Composable
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.components.dialogs.ConfirmationDialog
import io.kdot.app.designsystem.components.dialogs.ErrorDialog
import io.kdot.app.designsystem.components.dialogs.ProgressDialog
import net.folivo.trixnity.core.model.RoomId
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LeaveRoomView(
    leaveRoomState: LeaveRoomState,
    eventSink: (LeaveRoomEvent) -> Unit,
) {
    LeaveRoomConfirmationDialog(leaveRoomState, eventSink)
    LeaveRoomProgressDialog(leaveRoomState)
    LeaveRoomErrorDialog(leaveRoomState, eventSink)
}

@Composable
private fun LeaveRoomConfirmationDialog(
    state: LeaveRoomState,
    eventSink: (LeaveRoomEvent) -> Unit,
) {
    when (state.confirmation) {
        is LeaveRoomState.Confirmation.Hidden -> {}


        is LeaveRoomState.Confirmation.Dm -> LeaveRoomConfirmationDialog(
            text = Resources.String.leave_conversation_alert_subtitle,
            roomId = state.confirmation.roomId,
            isDm = true,
            eventSink = eventSink,
        )

        is LeaveRoomState.Confirmation.PrivateRoom -> LeaveRoomConfirmationDialog(
            text = Resources.String.leave_room_alert_private_subtitle,
            roomId = state.confirmation.roomId,
            isDm = false,
            eventSink = eventSink,
        )

        is LeaveRoomState.Confirmation.LastUserInRoom -> LeaveRoomConfirmationDialog(
            text = Resources.String.leave_room_alert_empty_subtitle,
            roomId = state.confirmation.roomId,
            isDm = false,
            eventSink = eventSink,
        )

        is LeaveRoomState.Confirmation.Generic -> LeaveRoomConfirmationDialog(
            text = Resources.String.leave_room_alert_subtitle,
            roomId = state.confirmation.roomId,
            isDm = false,
            eventSink = eventSink,
        )
    }
}

@Composable
private fun LeaveRoomConfirmationDialog(
    text: StringResource,
    roomId: RoomId,
    isDm: Boolean,
    eventSink: (LeaveRoomEvent) -> Unit,
) {
    ConfirmationDialog(
        title = stringResource(if (isDm) Resources.String.action_leave_conversation else Resources.String.action_leave_room),
        content = stringResource(text),
        submitText = stringResource(Resources.String.action_leave),
        onSubmitClick = { eventSink(LeaveRoomEvent.LeaveRoom(roomId)) },
        onDismiss = { eventSink(LeaveRoomEvent.HideConfirmation) },
    )
}

@Composable
private fun LeaveRoomProgressDialog(
    state: LeaveRoomState,
) {
    when (state.progress) {
        is LeaveRoomState.Progress.Hidden -> {}
        is LeaveRoomState.Progress.Shown -> ProgressDialog(
            text = stringResource(Resources.String.common_leaving_room),
        )
    }
}

@Composable
private fun LeaveRoomErrorDialog(
    state: LeaveRoomState,
    eventSink: (LeaveRoomEvent) -> Unit,
) {
    when (state.error) {
        is LeaveRoomState.Error.Hidden -> {}
        is LeaveRoomState.Error.Shown -> ErrorDialog(
            content = stringResource(Resources.String.error_unknown),
            onSubmit = { eventSink(LeaveRoomEvent.HideError) }
        )
    }
}

