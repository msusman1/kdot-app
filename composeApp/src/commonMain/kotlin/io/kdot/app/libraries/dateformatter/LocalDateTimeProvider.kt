/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.dateformatter

import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime


class LocalDateTimeProvider(
    val clock: Clock,
    val timeZone: TimeZone,
) {

    fun providesNow(): LocalDateTime {
        val now: Instant = clock.now()
        return now.toLocalDateTime(timeZone)
    }

    fun providesFromTimestamp(timestamp: Long): LocalDateTime {
        val tsInstant = Instant.fromEpochMilliseconds(timestamp)
        return tsInstant.toLocalDateTime(timeZone)
    }
}
