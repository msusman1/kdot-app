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

interface DateFormatter {
    fun format(
        timestamp: Long?,
        mode: DateFormatterMode = DateFormatterMode.Full,
        useRelative: Boolean = false,
    ): String
}

enum class DateFormatterMode {
    /**
     * Full date and time.
     * Example:
     * "April 6, 1980 at 6:35 PM"
     * Format can be shorter when useRelative is true.
     * Example:
     * "6:35 PM"
     */
    Full,

    /**
     * Only month and year.
     * Example:
     * "April 1980"
     * "This month" can be returned when useRelative is true.
     * Example:
     * "This month"
     */
    Month,

    /**
     * Only day.
     * Example:
     * "Sunday 6 April"
     * "Today", "Yesterday" and day of week can be returned when useRelative is true.
     */
    Day,

    /**
     * Time if same day, else date.
     */
    TimeOrDate,

    /**
     * Only time whatever the day.
     */
    TimeOnly,
}


class DateFormatterImpl(
    private val dateFormatterFull: DateFormatterFull,
    private val dateFormatterMonth: DateFormatterMonth,
    private val dateFormatterDay: DateFormatterDay,
    private val dateFormatterTime: DateFormatterTime,
    private val dateFormatterTimeOnly: DateFormatterTimeOnly,
) : DateFormatter {
    override fun format(
        timestamp: Long?,
        mode: DateFormatterMode,
        useRelative: Boolean
    ): String {
        timestamp ?: return ""
        return when (mode) {
            DateFormatterMode.Full -> {
                dateFormatterFull.format(timestamp, useRelative)
            }

            DateFormatterMode.Month -> {
                dateFormatterMonth.format(timestamp, useRelative)
            }

            DateFormatterMode.Day -> {
                dateFormatterDay.format(timestamp, useRelative)
            }

            DateFormatterMode.TimeOrDate -> {
                dateFormatterTime.format(timestamp, useRelative)
            }

            DateFormatterMode.TimeOnly -> {
                dateFormatterTimeOnly.format(timestamp)
            }
        }
    }
}