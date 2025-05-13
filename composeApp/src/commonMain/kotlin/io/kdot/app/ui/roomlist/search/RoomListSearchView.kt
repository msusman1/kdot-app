/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.ui.roomlist.search

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.consumeWindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material.icons.filled.FormatListBulleted
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.dp
import io.element.android.libraries.designsystem.utils.copy
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.components.buttons.BackButton
import io.kdot.app.designsystem.components.buttons.SuperButton
import io.kdot.app.designsystem.modifiers.applyIf
import io.kdot.app.designsystem.theme.ButtonSize
import io.kdot.app.designsystem.theme.components.FilledTextField
import io.kdot.app.domain.RoomListRoomSummary
import io.kdot.app.ui.roomlist.RoomListEvents
import io.kdot.app.ui.roomlist.RoomSummaryRow
import net.folivo.trixnity.core.model.RoomId
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun RoomListSearchView(
    state: RoomListSearchState,
    eventSinkRoomListSearch: (RoomListSearchEvents) -> Unit,
    eventSinkRoomList: (RoomListEvents) -> Unit,
    onRoomClick: (RoomId) -> Unit,
    onRoomDirectorySearchClick: () -> Unit,
    modifier: Modifier = Modifier,
) {

    AnimatedVisibility(
        visible = state.isSearchActive,
        enter = fadeIn(),
        exit = fadeOut(),
    ) {
        Column(
            modifier = modifier
                .applyIf(
                    condition = state.isSearchActive,
                    ifTrue = {
                        // Disable input interaction to underlying views
                        pointerInput(Unit) {}
                    }
                )
        ) {
            if (state.isSearchActive) {
                RoomListSearchContent(
                    state = state,
                    onRoomClick = onRoomClick,
                    eventSinkRoomListSearch = eventSinkRoomListSearch,
                    eventSinkRoomList = eventSinkRoomList,
                    onRoomDirectorySearchClick = onRoomDirectorySearchClick,
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun RoomListSearchContent(
    state: RoomListSearchState,
    eventSinkRoomListSearch: (RoomListSearchEvents) -> Unit,
    eventSinkRoomList: (RoomListEvents) -> Unit,
    onRoomClick: (RoomId) -> Unit,
    onRoomDirectorySearchClick: () -> Unit,
) {
    val borderColor = MaterialTheme.colorScheme.tertiary
    val strokeWidth = 1.dp
    fun onBackButtonClick() {
        eventSinkRoomListSearch(RoomListSearchEvents.ToggleSearchVisibility)
    }

    fun onRoomClick(room: RoomListRoomSummary) {
        onRoomClick(room.roomId)
    }
    Scaffold(
        topBar = {
            TopAppBar(
                modifier = Modifier.drawBehind {
                    drawLine(
                        color = borderColor,
                        start = Offset(0f, size.height),
                        end = Offset(size.width, size.height),
                        strokeWidth = strokeWidth.value
                    )
                },
                navigationIcon = { BackButton(onClick = ::onBackButtonClick) },
                title = {
                    val filter = state.query
                    val focusRequester = FocusRequester()
                    FilledTextField(
                        modifier = Modifier
                            .fillMaxWidth()
                            .focusRequester(focusRequester),
                        value = filter,
                        singleLine = true,
                        onValueChange = {
                            eventSinkRoomListSearch(
                                RoomListSearchEvents.QueryChanged(
                                    it
                                )
                            )
                        },
                        colors = TextFieldDefaults.colors(
                            focusedContainerColor = Color.Transparent,
                            unfocusedContainerColor = Color.Transparent,
                            disabledContainerColor = Color.Transparent,
                            focusedIndicatorColor = Color.Transparent,
                            unfocusedIndicatorColor = Color.Transparent,
                            disabledIndicatorColor = Color.Transparent,
                            errorIndicatorColor = Color.Transparent,
                        ),
                        trailingIcon = {
                            if (filter.isNotEmpty()) {
                                IconButton(onClick = {
                                    eventSinkRoomListSearch(RoomListSearchEvents.ClearQuery)
                                }) {
                                    Icon(
                                        imageVector = Icons.Default.Close,
                                        contentDescription = stringResource(Resources.String.action_cancel)
                                    )
                                }
                            }
                        }
                    )

                    LaunchedEffect(state.isSearchActive) {
                        if (state.isSearchActive) {
                            focusRequester.requestFocus()
                        }
                    }
                },
                windowInsets = TopAppBarDefaults.windowInsets.copy(top = 0)
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .padding(padding)
                .consumeWindowInsets(padding)
        ) {
            if (state.displayRoomDirectorySearch) {
                RoomDirectorySearchButton(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 24.dp, horizontal = 16.dp),
                    onClick = onRoomDirectorySearchClick
                )
            }
            LazyColumn(
                modifier = Modifier.weight(1f),
            ) {
                items(
                    items = state.results,
                    contentType = { room -> room.displayType.ordinal },
                ) { room ->
                    RoomSummaryRow(
                        room = room,
                        onClick = ::onRoomClick,
                        eventSinkRoomList = eventSinkRoomList,
                    )
                }
            }
        }
    }
}

@Composable
private fun RoomDirectorySearchButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    SuperButton(
        onClick = onClick,
        modifier = modifier,
        buttonSize = ButtonSize.Large,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = Icons.Default.FormatListBulleted,
                contentDescription = null,
            )
            Spacer(modifier = Modifier.width(8.dp))
            Text(
                text = stringResource(Resources.String.screen_roomlist_room_directory_button_title),
            )
        }
    }
}

