/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.matrixui.media


/**
 * Can be use with [coil.compose.AsyncImage] to load a [MediaSource].
 * This will go internally through our [CoilMediaFetcher].
 *
 * Example of usage:
 *  AsyncImage(
 *      model = MediaRequestData(mediaSource, MediaRequestData.Kind.Content),
 *      contentScale = ContentScale.Fit,
 *  )
 *
 */


data class MediaSource(
    /**
     * Url of the media.
     */
    val url: String,
    /**
     * This is used to hold data for encrypted media.
     */
    val json: String? = null,
)


data class MediaRequestData(
    val source: MediaSource?,
    val kind: Kind
) {
    sealed interface Kind {
        data object Content : Kind

        data class File(
            val fileName: String,
            val mimeType: String,
        ) : Kind

        data class Thumbnail(val width: Long, val height: Long) : Kind {
            constructor(size: Long) : this(size, size)
        }
    }
}

/** Max width a thumbnail can have according to [the spec](https://spec.matrix.org/v1.10/client-server-api/#thumbnails). */
const val MAX_THUMBNAIL_WIDTH = 800L

/** Max height a thumbnail can have according to [the spec](https://spec.matrix.org/v1.10/client-server-api/#thumbnails). */
const val MAX_THUMBNAIL_HEIGHT = 600L
