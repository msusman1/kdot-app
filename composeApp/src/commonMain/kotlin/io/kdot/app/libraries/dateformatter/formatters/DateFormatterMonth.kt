/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.dateformatter.formatters

import io.kdot.app.libraries.dateformatter.DateFormatters
import io.kdot.app.libraries.dateformatter.LocalDateTimeProvider

fun String.safeCapitalize(): String {
    return replaceFirstChar {
        if (it.isLowerCase()) {
            it.titlecase()
        } else {
            it.toString()
        }
    }
}

class DateFormatterMonth(
    private val localDateTimeProvider: LocalDateTimeProvider,
    private val dateFormatters: DateFormatters,
) {
    fun format(
        timestamp: Long,
        useRelative: Boolean,
    ): String {
        val today = localDateTimeProvider.providesNow()
        val dateToFormat = localDateTimeProvider.providesFromTimestamp(timestamp)
        return if (useRelative && dateToFormat.month == today.month && dateToFormat.year == today.year) {
            "This month"
        } else {
            dateFormatters.formatDateWithMonthAndYear(dateToFormat)
        }
            .safeCapitalize()
    }
}
