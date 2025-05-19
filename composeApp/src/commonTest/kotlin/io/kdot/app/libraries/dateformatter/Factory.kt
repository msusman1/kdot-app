/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.dateformatter

import io.kdot.app.libraries.dateformatter.formatters.DateFormatterDay
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterFull
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterMonth
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterTime
import io.kdot.app.libraries.dateformatter.formatters.DateFormatterTimeOnly
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.TimeZone

class FakeClock : Clock {
    private var instant: Instant = Instant.fromEpochMilliseconds(0)

    fun givenInstant(instant: Instant) {
        this.instant = instant
    }

    override fun now(): Instant = instant
}


fun createFormatter(currentDate: String): DateFormatterImpl {
    val clock = FakeClock().apply { givenInstant(Instant.parse(currentDate)) }
    val localDateTimeProvider = LocalDateTimeProvider(
        clock = clock,
        timeZone = TimeZone.UTC
    )
    val dateFormatters = DateFormatters(
        clock = clock,
        timeZone = TimeZone.currentSystemDefault()
    )
    val dateFormatterDay = DateFormatterDay(
        localDateTimeProvider = localDateTimeProvider,
        dateFormatters = dateFormatters,
    )
    return DateFormatterImpl(
        dateFormatterFull = DateFormatterFull(
            localDateTimeProvider = localDateTimeProvider,
            dateFormatters = dateFormatters,
            dateFormatterDay = dateFormatterDay,
        ),
        dateFormatterMonth = DateFormatterMonth(
            localDateTimeProvider = localDateTimeProvider,
            dateFormatters = dateFormatters,
        ),
        dateFormatterDay = dateFormatterDay,
        dateFormatterTime = DateFormatterTime(
            localDateTimeProvider = localDateTimeProvider,
            dateFormatters = dateFormatters,
        ),
        dateFormatterTimeOnly = DateFormatterTimeOnly(
            localDateTimeProvider = localDateTimeProvider,
            dateFormatters = dateFormatters,
        ),
    )
}
