/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.dateformatter

import io.kdot.app.utils.formatDate
import kotlinx.datetime.Clock
import kotlinx.datetime.Instant
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.daysUntil
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import kotlin.math.abs


class DateFormatters(
    private val clock: Clock,
    private val timeZone: TimeZone,
) {


    internal fun formatTime(localDateTime: LocalDateTime): String {
        val pattern = "h:mm a"
        return localDateTime.toInstant(timeZone).formatDate(pattern)
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
        val pattern = "MMMM d, yyyy"
        return localDateTime.toInstant(timeZone).formatDate(pattern)
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
        val date = dateToFormat.date
        val current = currentDate.date

        val yearsDiff = current.year - date.year
        val monthsDiff = current.monthNumber - date.monthNumber
        val daysDiff = current.dayOfMonth - date.dayOfMonth

        return when {
            abs(yearsDiff) >= 1 -> {
                formatDateWithYear(dateToFormat)
            }

            useRelative && abs(daysDiff) < 2 && monthsDiff == 0 -> {
                getRelativeDay(dateToFormat.toInstant(timeZone).toEpochMilliseconds())
            }

            else -> {
                formatDateWithMonth(dateToFormat)
            }
        }
    }

    internal fun getRelativeDay(
        ts: Long,
        default: String = ""
    ): String {
        return try {
            val now = clock.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            val date = Instant.fromEpochMilliseconds(ts)
                .toLocalDateTime(TimeZone.currentSystemDefault()).date
            val daysDiff = date.daysUntil(now)

            when (daysDiff) {
                0 -> "Today"
                1 -> "Yesterday"
                in 2..6 -> date.dayOfWeek.name.lowercase()
                    .replaceFirstChar { it.uppercase() } // e.g., "Monday"
                else -> "${abs(daysDiff)} days ago"
            }
        } catch (e: Exception) {
            default
        }
    }
}
