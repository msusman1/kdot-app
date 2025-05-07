/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.features.networkmonitor

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.core.MutableTransitionState
import androidx.compose.animation.expandVertically
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier

/**
 * A view that displays a connectivity indicator when the device is offline, adding a default
 * padding to make sure the status bar is not overlapped.
 */
@Composable
fun ConnectivityIndicatorView(
    isOnline: Boolean,
) {
    val isIndicatorVisible = remember { MutableTransitionState(!isOnline) }.apply { targetState = !isOnline }
    val isStatusBarPaddingVisible = remember { MutableTransitionState(isOnline) }.apply { targetState = isOnline }

    // Display the network indicator with an animation
    AnimatedVisibility(
        visibleState = isIndicatorVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
    ) {
        Indicator()
    }

    // Show missing status bar padding when the indicator is not visible
    AnimatedVisibility(
        visibleState = isStatusBarPaddingVisible,
        enter = fadeIn() + expandVertically(),
        exit = fadeOut() + shrinkVertically(),
    ) {
        StatusBarPaddingSpacer()
    }
}

@Composable
private fun StatusBarPaddingSpacer(modifier: Modifier = Modifier) {
    Spacer(modifier = modifier.statusBarsPadding())
}

