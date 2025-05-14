/*
 * Copyright 2022-2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.matrixui.media

import coil3.key.Keyer
import coil3.request.Options
import io.kdot.app.domain.AvatarData

const val AVATAR_THUMBNAIL_SIZE_IN_PIXEL = 240L

internal fun AvatarData.toMediaRequestData(): MediaRequestData {
    return MediaRequestData(
        source = url?.let { MediaSource(it) },
        kind = MediaRequestData.Kind.Thumbnail(AVATAR_THUMBNAIL_SIZE_IN_PIXEL)
    )
}


internal class AvatarDataKeyer : Keyer<AvatarData> {
    override fun key(data: AvatarData, options: Options): String? {
        return data.toMediaRequestData().toKey()
    }
}

internal class MediaRequestDataKeyer : Keyer<MediaRequestData> {
    override fun key(data: MediaRequestData, options: Options): String? {
        return data.toKey()
    }
}

private fun MediaRequestData.toKey(): String? {
    if (source == null) return null
    return "${source.url}_$kind"
}
