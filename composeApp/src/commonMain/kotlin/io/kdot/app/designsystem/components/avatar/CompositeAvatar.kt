/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.designsystem.components.avatar

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.unit.dp
import io.kdot.app.domain.AvatarData
import io.kdot.app.libraries.core.data.swap
import kotlin.math.PI
import kotlin.math.cos
import kotlin.math.sin

@Composable
fun CompositeAvatar(
    avatarData: AvatarData,
    heroes: List<AvatarData>,
    modifier: Modifier = Modifier,
    contentDescription: String? = null,
) {
    if (avatarData.url != null || heroes.isEmpty()) {
        Avatar(avatarData, modifier, contentDescription)
    } else {
        val limitedHeroes: MutableList<AvatarData> = heroes.take(4).toMutableList()
        val numberOfHeroes = limitedHeroes.size
        if (numberOfHeroes == 4) {
            // Swap 2 and 3 so that the 4th hero is at the bottom right
            limitedHeroes.swap(2, 3)
        }
        when (numberOfHeroes) {
            0 -> {
                error("Unsupported number of heroes: 0")
            }

            1 -> {
                Avatar(heroes[0], modifier, contentDescription)
            }

            else -> {
                val angle = 2 * PI / numberOfHeroes
                val offsetRadius = when (numberOfHeroes) {
                    2 -> avatarData.size.dp.value / 4.2
                    3 -> avatarData.size.dp.value / 4.0
                    4 -> avatarData.size.dp.value / 3.1
                    else -> error("Unsupported number of heroes: $numberOfHeroes")
                }
                val heroAvatarSize = when (numberOfHeroes) {
                    2 -> avatarData.size.dp / 2.2f
                    3 -> avatarData.size.dp / 2.4f
                    4 -> avatarData.size.dp / 2.2f
                    else -> error("Unsupported number of heroes: $numberOfHeroes")
                }
                val angleOffset = when (numberOfHeroes) {
                    2 -> PI
                    3 -> 7 * PI / 6
                    4 -> 13 * PI / 4
                    else -> error("Unsupported number of heroes: $numberOfHeroes")
                }
                Box(
                    modifier = modifier
                        .size(avatarData.size.dp)
                        .semantics {
                            this.contentDescription = contentDescription.orEmpty()
                        },
                    contentAlignment = Alignment.Center,
                ) {
                    limitedHeroes.forEachIndexed { index, heroAvatar ->
                        val xOffset =
                            (offsetRadius * cos(angle * index.toDouble() + angleOffset)).dp
                        val yOffset =
                            (offsetRadius * sin(angle * index.toDouble() + angleOffset)).dp
                        Box(
                            modifier = Modifier
                                .size(heroAvatarSize)
                                .offset(
                                    x = xOffset,
                                    y = yOffset,
                                )
                        ) {
                            Avatar(
                                heroAvatar,
                                forcedAvatarSize = heroAvatarSize,
                            )
                        }
                    }
                }
            }
        }
    }
}

