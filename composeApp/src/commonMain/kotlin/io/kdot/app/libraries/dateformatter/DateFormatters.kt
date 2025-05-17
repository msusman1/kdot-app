/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.dateformatter

import io.kdot.app.utils.formatDate
import kotlinx.datetime.Clock
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toInstant
import kotlin.math.absoluteValue


class DateFormatters(


) {
    private val clock: Clock = Clock.System
    private val timeZone: TimeZone = TimeZone.currentSystemDefault()


    internal fun formatTime(localDateTime: LocalDateTime): String {

        val onlyTimeFormatter =
            DateTimeFormatter.ofLocalizedTime(FormatStyle.SHORT).withLocale(locale)
        return onlyTimeFormatter.format(localDateTime)
    }

    internal fun formatDateWithMonthAndYear(localDateTime: LocalDateTime): String {
        val pattern = "MMMM YYYY"
        return localDateTime.toInstant(timeZone).formatDate(pattern)
    }

    internal fun formatDateWithMonth(localDateTime: LocalDateTime): String {
        val pattern = "d MMM"
        return localDateTime.toInstant(timeZone).formatDate(pattern)
    }

    internal fun formatDateWithDay(localDateTime: LocalDateTime): String {
        val pattern = "EEEE"
        return localDateTime.toInstant(timeZone).formatDate(pattern)
    }

    internal fun formatDateWithYear(localDateTime: LocalDateTime): String {
        val pattern = "dd.MM.yyyy"
        return localDateTime.toInstant(timeZone).formatDate(pattern)
    }

    internal fun formatDateWithFullFormat(localDateTime: LocalDateTime): String {
        val dateWithFullFormatFormatter = DateTimeFormatter.ofLocalizedDate(FormatStyle.LONG).withLocale(locale)
        return dateWithFullFormatFormatter.format(localDateTime)
    }

    internal fun formatDateWithFullFormatNoYear(localDateTime: LocalDateTime): String {
        val pattern = "EEEE d MMMM"
        return localDateTime.toInstant(timeZone).formatDate(pattern)
    }


    internal fun formatDate(
        dateToFormat: LocalDateTime,
        currentDate: LocalDateTime,
        useRelative: Boolean
    ): String {
        val period =
            Period.between(dateToFormat.date.toJavaLocalDate(), currentDate.date.toJavaLocalDate())
        return if (period.years.absoluteValue >= 1) {
            formatDateWithYear(dateToFormat)
        } else if (useRelative && period.days.absoluteValue < 2 && period.months.absoluteValue < 1) {
            getRelativeDay(dateToFormat.toInstant(timeZone).toEpochMilliseconds())
        } else {
            formatDateWithMonth(dateToFormat)
        }
    }

    internal fun getRelativeDay(ts: Long, default: String = ""): String {
        return DateUtils.getRelativeTimeSpanString(
            ts,
            clock.now().toEpochMilliseconds(),
            DateUtils.DAY_IN_MILLIS,
            DateUtils.FORMAT_SHOW_WEEKDAY
        )?.toString() ?: default
    }
}
