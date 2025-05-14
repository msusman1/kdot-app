/*
 * Copyright 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.matrixui.media

import coil3.ImageLoader
import coil3.fetch.Fetcher
import coil3.request.Options
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.media


internal class MediaRequestDataFetcherFactory(
    private val client: MatrixClient
) : Fetcher.Factory<MediaRequestData> {
    override fun create(
        data: MediaRequestData,
        options: Options,
        imageLoader: ImageLoader
    ): Fetcher {
        return CoilMediaFetcher(
            mediaService = client.media,
            mediaData = data,
            options = options
        )
    }
}
