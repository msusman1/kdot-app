/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.dateformatter.formatters

import io.kdot.app.libraries.dateformatter.DateFormatters
import io.kdot.app.libraries.dateformatter.LocalDateTimeProvider


class DateFormatterFull (
    private val localDateTimeProvider: LocalDateTimeProvider,
    private val dateFormatters: DateFormatters,
    private val dateFormatterDay: DateFormatterDay,
) {
    fun format(
        timestamp: Long,
        useRelative: Boolean,
    ): String {
        val dateToFormat = localDateTimeProvider.providesFromTimestamp(timestamp)
        val time = dateFormatters.formatTime(dateToFormat)
        return if (useRelative) {
            val now = localDateTimeProvider.providesNow()
            if (now.date == dateToFormat.date) {
               time
            } else {
                val dateStr = dateFormatterDay.format(timestamp, true)
                "$dateStr at $time"  // todo extract to string resource
            }
        } else {
            val dateStr = dateFormatters.formatDateWithFullFormat(dateToFormat)
            "$dateStr at $time"
        }
    }
}
