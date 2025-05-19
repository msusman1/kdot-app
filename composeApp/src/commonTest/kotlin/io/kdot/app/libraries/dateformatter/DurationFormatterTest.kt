/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.dateformatter

 
import kotlin.test.Test
import kotlin.test.assertEquals

class DurationFormatterTest {
    @Test
    fun `format seconds only`() {
        assertEquals(buildDuration().toHumanReadableDuration(),"0:00")
        assertEquals(buildDuration(seconds = 1).toHumanReadableDuration(),"0:01")
        assertEquals(buildDuration(seconds = 59).toHumanReadableDuration(),"0:59")
    }

    @Test
    fun `format minutes and seconds`() {
        assertEquals(buildDuration(minutes = 1).toHumanReadableDuration(),"1:00")
        assertEquals(buildDuration(minutes = 1, seconds = 30).toHumanReadableDuration(),"1:30")
        assertEquals(buildDuration(minutes = 59, seconds = 59).toHumanReadableDuration(),"59:59")
    }

    @Test
    fun `format hours minutes and seconds`() {
        assertEquals(buildDuration(hours = 1).toHumanReadableDuration(),"1:00:00")
        assertEquals(buildDuration(hours = 2, minutes = 10, seconds = 9).toHumanReadableDuration(),"2:10:09")
        assertEquals(buildDuration(hours = 24, minutes = 59, seconds = 59).toHumanReadableDuration(),"24:59:59")
        assertEquals(buildDuration(hours = 25, minutes = 0, seconds = 0).toHumanReadableDuration(),"25:00:00")
    }

    private fun buildDuration(
        hours: Int = 0,
        minutes: Int = 0,
        seconds: Int = 0
    ): Long {
        return (hours * 60 * 60 + minutes * 60 + seconds) * 1000L
    }
}
