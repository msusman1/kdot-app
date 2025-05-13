/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.ui.roomlist.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Badge
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.MediumTopAppBar
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.TopAppBarScrollBehavior
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.unit.dp
import io.kdot.app.designsystem.Resources
import io.kdot.app.designsystem.components.avatar.Avatar
import io.kdot.app.designsystem.components.avatar.AvatarSize
import io.kdot.app.designsystem.text.applyScaleDown
import io.kdot.app.designsystem.text.roundToPx
import io.kdot.app.designsystem.text.toSp
import io.kdot.app.domain.AvatarData
import io.kdot.app.ui.roomlist.MatrixUser
import io.kdot.app.ui.roomlist.filter.RoomListFilterEvents
import io.kdot.app.ui.roomlist.filter.RoomListFilterState
import io.kdot.app.ui.roomlist.filter.RoomListFiltersView
import io.kdot.app.ui.roomlist.getAvatarData
import io.kdot.app.ui.theme.appTypography
import org.jetbrains.compose.resources.stringResource


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RoomListTopBar(
    matrixUser: MatrixUser,
    showAvatarIndicator: Boolean,
    onToggleSearch: () -> Unit,
    onOpenSettings: () -> Unit,
    scrollBehavior: TopAppBarScrollBehavior,
    displayFilters: Boolean,
    roomListFilterState: RoomListFilterState,
    eventSinkRoomListFilter: (RoomListFilterEvents) -> Unit,
    modifier: Modifier = Modifier,
) {
    // We need this to manually clip the top app bar in preview mode
    val previewAppBarHeight = if (LocalInspectionMode.current) {
        112.dp.roundToPx()
    } else {
        null
    }
    val collapsedFraction = scrollBehavior.state.collapsedFraction
    var appBarHeight by remember {
        mutableIntStateOf(previewAppBarHeight ?: 0)
    }

    val avatarData by remember(matrixUser) {
        derivedStateOf {
            matrixUser.getAvatarData(size = AvatarSize.CurrentUserTopBar)
        }
    }

    Box(modifier = modifier) {
        val collapsedTitleTextStyle = MaterialTheme.appTypography.fontHeadingSmMedium
        val expandedTitleTextStyle = MaterialTheme.appTypography.fontHeadingLgBold.copy(
            // Due to a limitation of MediumTopAppBar, and to avoid the text to be truncated,
            // ensure that the font size will never be bigger than 28.dp.
            fontSize = 28.dp.applyScaleDown().toSp()
        )
        MaterialTheme(
            colorScheme = MaterialTheme.colorScheme,
            shapes = MaterialTheme.shapes,
            typography = MaterialTheme.typography.copy(
                headlineSmall = expandedTitleTextStyle,
                titleLarge = collapsedTitleTextStyle
            ),
        ) {
            Column(
                modifier = Modifier
                    .onSizeChanged {
                        appBarHeight = it.height
                    }
                    .statusBarsPadding(),
            ) {
                MediumTopAppBar(
                    colors = TopAppBarDefaults.mediumTopAppBarColors(
                        containerColor = Color.Transparent,
                        scrolledContainerColor = Color.Transparent,
                    ),
                    title = {
                        Text(text = stringResource(Resources.String.screen_roomlist_main_space_title))
                    },
                    navigationIcon = {
                        NavigationIcon(
                            avatarData = avatarData,
                            showAvatarIndicator = showAvatarIndicator,
                            onClick = onOpenSettings,
                        )
                    },
                    actions = {
                        IconButton(
                            onClick = onToggleSearch,
                        ) {
                            Icon(
                                imageVector = Icons.Default.Search,
                                contentDescription = stringResource(Resources.String.search),
                            )
                        }

                    },
                    scrollBehavior = scrollBehavior,
                    windowInsets = WindowInsets(0.dp),
                )
                if (displayFilters) {
                    RoomListFiltersView(
                        roomListFilterState = roomListFilterState,
                        eventSink = eventSinkRoomListFilter,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }
            }
        }

        HorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .alpha(collapsedFraction)
                .align(Alignment.BottomCenter),
            color = MaterialTheme.colorScheme.outlineVariant,
        )
    }
}

@Composable
private fun NavigationIcon(
    avatarData: AvatarData,
    showAvatarIndicator: Boolean,
    onClick: () -> Unit,
) {
    IconButton(
        onClick = onClick,
    ) {
        Box {
            Avatar(
                avatarData = avatarData,
                contentDescription = stringResource(Resources.String.common_settings),
            )
            if (showAvatarIndicator) {
                Badge(
                    modifier = Modifier.align(Alignment.TopEnd)
                )
            }
        }
    }
}
