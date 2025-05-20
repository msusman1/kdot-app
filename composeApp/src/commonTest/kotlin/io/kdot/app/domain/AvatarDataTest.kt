/*
 * Copyright 2025 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.domain

import io.kdot.app.designsystem.components.avatar.AvatarSize
import kotlin.test.Test
import kotlin.test.assertEquals


class AvatarDataTest {
    @Test
    fun `initial with text should get the first char, uppercased`() {
        val data = AvatarData("id", "test", null, AvatarSize.InviteSender)
        assertEquals(data.initial, "T")
    }

    @Test
    fun `initial with leading whitespace should get the first non-whitespace char, uppercased`() {
        val data = AvatarData("id", " test", null, AvatarSize.InviteSender)
        assertEquals(data.initial, "T")
    }


    @Test
    fun `initial with short emoji should get the emoji`() {
        val data = AvatarData("id", "✂ Test", null, AvatarSize.InviteSender)
        assertEquals(data.initial, "✂")
    }

    @Test
    fun `initial with a single letter should take that letter`() {
        val data = AvatarData("id", "m", null, AvatarSize.InviteSender)
        assertEquals(data.initial, "M")
    }

    @Test
    fun `initial with a empty name should return defulat hash`() {
        val data = AvatarData("!id:domain", "", null, AvatarSize.InviteSender)
        assertEquals(data.initial, "#")
    }
}
