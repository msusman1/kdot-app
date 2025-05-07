/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.components.buttons

import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import io.kdot.app.designsystem.theme.TextButton
import io.kdot.app.ui.roomlist.IconSource
import org.jetbrains.compose.resources.painterResource

/**
 * A sealed interface that represents the different visual styles that a button can have.
 */
@Immutable
sealed interface ButtonVisuals {
    val action: () -> Unit

    /**
     * Creates a [Button] composable based on the visual state.
     */
    @Composable
    fun Composable()

    data class Text(val text: String, override val action: () -> Unit) : ButtonVisuals {
        @Composable
        override fun Composable() {
            TextButton(text = text, onClick = action)
        }
    }

    data class Icon(val iconSource: IconSource, override val action: () -> Unit) : ButtonVisuals {
        @Composable
        override fun Composable() {
            IconButton(onClick = action) {
                when (iconSource) {
                    is IconSource.Resources -> Icon(
                        painter = painterResource(iconSource.drawableResource),
                        contentDescription = null
                    )

                    is IconSource.Vector -> Icon(
                        imageVector = iconSource.imageVector,
                        contentDescription = null
                    )
                }

            }
        }
    }
}
