/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.dateformatter

import kotlinx.datetime.Instant
import kotlin.test.Test
import kotlin.test.assertEquals


class DateFormatterImplTest {
    @Test
    fun `test null`() {
        val now = "1980-04-06T18:35:24.00Z"
        val ts: Long? = null
        val formatter = createFormatter(now)
        assertEquals(
            expected = "",
            actual = formatter.format(ts)
        )
    }

    @Test
    fun `test epoch`() {
        val now = "1980-04-06T18:35:24.00Z"
        val ts = 0L
        val formatter = createFormatter(now)
        assertEquals(
            expected = "January 1, 1970 at 12:00 AM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "January 1970",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "January 1, 1970",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "01.01.1970",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "12:00 AM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test epoch relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val ts = 0L
        val formatter = createFormatter(now)
        assertEquals(
            expected = "January 1, 1970 at 12:00 AM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "January 1970",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "January 1, 1970",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "01.01.1970",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "12:00 AM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }

    @Test
    fun `test now`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-06T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "April 6, 1980 at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "April 1980",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "Sunday 6 April",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test now relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-06T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "This month",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "Today",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }

    @Test
    fun `test one second before`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-06T18:35:23.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "April 6, 1980 at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "April 1980",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "Sunday 6 April",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test one second before relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-06T18:35:23.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "This month",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "Today",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }

    @Test
    fun `test one minute before`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-06T18:34:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "April 6, 1980 at 6:34 PM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "April 1980",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "Sunday 6 April",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "6:34 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "6:34 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test one minute before relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-06T18:34:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "6:34 PM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "This month",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "Today",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "6:34 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "6:34 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }

    @Test
    fun `test one hour before`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-06T17:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "April 6, 1980 at 5:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "April 1980",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "Sunday 6 April",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "5:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "5:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test one hour before relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-06T17:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "5:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "This month",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "Today",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "5:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "5:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }

    @Test
    fun `test one day before same time`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-05T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "April 5, 1980 at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "April 1980",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "Saturday 5 April",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "5 Apr",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test one day before same time relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-05T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "Yesterday at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "This month",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "Yesterday",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "Yesterday",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }

    @Test
    fun `test two days before same time`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-04T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "April 4, 1980 at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "April 1980",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "Friday 4 April",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "4 Apr",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test two days before same time relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-04-04T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "Friday at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "This month",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "Friday",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "4 Apr",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }

    @Test
    fun `test one month before same time`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-03-06T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "March 6, 1980 at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "March 1980",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "Thursday 6 March",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "6 Mar",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test one month before same time relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1980-03-06T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "Thursday 6 March at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "March 1980",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "Thursday 6 March",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "6 Mar",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }

    @Test
    fun `test one year before same time`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1979-04-06T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "April 6, 1979 at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full)
        )
        assertEquals(
            expected = "April 1979",
            actual = formatter.format(ts, DateFormatterMode.Month)
        )
        assertEquals(
            expected = "April 6, 1979",
            actual = formatter.format(ts, DateFormatterMode.Day)
        )
        assertEquals(
            expected = "06.04.1979",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly)
        )
    }

    @Test
    fun `test one year before same time relative`() {
        val now = "1980-04-06T18:35:24.00Z"
        val dat = "1979-04-06T18:35:24.00Z"
        val ts = Instant.parse(dat).toEpochMilliseconds()
        val formatter = createFormatter(now)
        assertEquals(
            expected = "April 6, 1979 at 6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.Full, true)
        )
        assertEquals(
            expected = "April 1979",
            actual = formatter.format(ts, DateFormatterMode.Month, true)
        )
        assertEquals(
            expected = "April 6, 1979",
            actual = formatter.format(ts, DateFormatterMode.Day, true)
        )
        assertEquals(
            expected = "06.04.1979",
            actual = formatter.format(ts, DateFormatterMode.TimeOrDate, true)
        )
        assertEquals(
            expected = "6:35 PM",
            actual = formatter.format(ts, DateFormatterMode.TimeOnly, true)
        )
    }
}
