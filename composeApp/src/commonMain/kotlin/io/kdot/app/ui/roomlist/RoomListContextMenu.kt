/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.ui.roomlist

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.MarkAsUnread
import androidx.compose.material.icons.filled.Markunread
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontStyle
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.components.list.ListItemContent
import io.kdot.app.designsystem.theme.ListItem
import io.kdot.app.designsystem.theme.ListItemStyle
import io.kdot.app.ui.theme.appTypography
import net.folivo.trixnity.core.model.RoomId
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomListContextMenu(
    contextMenu: RoomListState.ContextMenu.Shown,
    eventSink: (RoomListEvents.ContextMenuEvents) -> Unit,
    onRoomSettingsClick: (roomId: RoomId) -> Unit,
) {
    ModalBottomSheet(
        onDismissRequest = { eventSink(RoomListEvents.HideContextMenu) },
    ) {
        RoomListModalBottomSheetContent(
            contextMenu = contextMenu,
            onRoomMarkReadClick = {
                eventSink(RoomListEvents.HideContextMenu)
                eventSink(RoomListEvents.MarkAsRead(contextMenu.roomId))
            },
            onRoomMarkUnreadClick = {
                eventSink(RoomListEvents.HideContextMenu)
                eventSink(RoomListEvents.MarkAsUnread(contextMenu.roomId))
            },
            onRoomSettingsClick = {
                eventSink(RoomListEvents.HideContextMenu)
                onRoomSettingsClick(contextMenu.roomId)
            },
            onLeaveRoomClick = {
                eventSink(RoomListEvents.HideContextMenu)
                eventSink(RoomListEvents.LeaveRoom(contextMenu.roomId))
            },
            onFavoriteChange = { isFavorite ->
                eventSink(RoomListEvents.SetRoomIsFavorite(contextMenu.roomId, isFavorite))
            }
        )
    }
}

@Composable
fun RoomListModalBottomSheetContent(
    contextMenu: RoomListState.ContextMenu.Shown,
    onRoomSettingsClick: () -> Unit,
    onLeaveRoomClick: () -> Unit,
    onFavoriteChange: (isFavorite: Boolean) -> Unit,
    onRoomMarkReadClick: () -> Unit,
    onRoomMarkUnreadClick: () -> Unit,
) {

    Column(
        modifier = Modifier.fillMaxWidth()
    ) {
        ListItem(
            headlineContent = {
                Text(
                    text = contextMenu.roomName
                        ?: stringResource(Resources.String.common_no_room_name),
                    style = MaterialTheme.appTypography.fontBodyLgMedium,
                    fontStyle = FontStyle.Italic.takeIf { contextMenu.roomName == null }
                )
            }
        )
        if (contextMenu.markAsUnreadFeatureFlagEnabled) {
            if (contextMenu.hasNewContent) {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(Resources.String.screen_roomlist_mark_as_read),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    onClick = onRoomMarkReadClick,
                    leadingContent = ListItemContent.Icon(
                        iconSource = IconSource.Vector(Icons.Default.Markunread)
                    ),
                    style = ListItemStyle.Primary,
                )
            } else {
                ListItem(
                    headlineContent = {
                        Text(
                            text = stringResource(Resources.String.screen_roomlist_mark_as_unread),
                            style = MaterialTheme.typography.bodyLarge,
                        )
                    },
                    onClick = onRoomMarkUnreadClick,
                    leadingContent = ListItemContent.Icon(
                        iconSource = IconSource.Vector(Icons.Default.MarkAsUnread)
                    ),
                    style = ListItemStyle.Primary,
                )
            }
        }
        ListItem(
            headlineContent = {
                Text(
                    text = stringResource(Resources.String.common_favourite),
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            leadingContent = ListItemContent.Icon(
                iconSource = IconSource.Vector(Icons.Default.Favorite)
            ),
            trailingContent = ListItemContent.Switch(
                checked = contextMenu.isFavorite,
                onChange = { isFavorite ->
                    onFavoriteChange(isFavorite)
                },
            ),
            onClick = {
                onFavoriteChange(!contextMenu.isFavorite)
            },
            style = ListItemStyle.Primary,
        )
        ListItem(
            headlineContent = {
                Text(
                    text = stringResource(Resources.String.common_settings),
                    style = MaterialTheme.typography.bodyLarge,
                )
            },
            modifier = Modifier.clickable { onRoomSettingsClick() },
            leadingContent = ListItemContent.Icon(
                iconSource = IconSource.Vector(Icons.Default.Settings)
            ),
            style = ListItemStyle.Primary,
        )
        ListItem(
            headlineContent = {
                val leaveText = stringResource(
                    if (contextMenu.isDm) {
                        Resources.String.action_leave_conversation
                    } else {
                        Resources.String.action_leave_room
                    }
                )
                Text(text = leaveText)
            },
            modifier = Modifier.clickable { onLeaveRoomClick() },
            leadingContent = ListItemContent.Icon(
                iconSource = IconSource.Resources(Resources.Icon.ic_compound_leave)
            ),
            style = ListItemStyle.Destructive,
        )
    }
}

sealed interface IconSource {
    data class Vector(val imageVector: ImageVector) : IconSource
    data class Resources(val drawableResource: DrawableResource) : IconSource
}