/*
 * Copyright 2023, 2024 New Vector Ltd.
 *
 * SPDX-License-Identifier: AGPL-3.0-only OR LicenseRef-Element-Commercial
 * Please see LICENSE files in the repository root for full details.
 */

package io.kdot.app.libraries.matrixui.media


import coil3.decode.DataSource
import coil3.decode.ImageSource
import coil3.fetch.FetchResult
import coil3.fetch.Fetcher
import coil3.fetch.SourceFetchResult
import coil3.request.Options
import io.github.aakira.napier.Napier
import io.kdot.app.matrix.platformFileSystem
import net.folivo.trixnity.client.media.MediaService
import net.folivo.trixnity.utils.toByteArray
import okio.Buffer
import okio.FileSystem

internal class CoilMediaFetcher(
    private val mediaService: MediaService,
    private val mediaData: MediaRequestData,
    private val options: Options
) : Fetcher {
    override suspend fun fetch(): FetchResult? {
        if (mediaData.source == null) {
            Napier.e("MediaData source is null")
            return null
        }
        return when (mediaData.kind) {
            is MediaRequestData.Kind.Content -> fetchContent(mediaData.source, options)
            is MediaRequestData.Kind.Thumbnail -> fetchThumbnail(
                mediaData.source,
                mediaData.kind,
                options
            )

            is MediaRequestData.Kind.File -> fetchFile(mediaData.source, mediaData.kind)
        }
    }

    /**
     * This method is here to avoid using [MatrixMediaLoader.loadMediaContent] as too many ByteArray allocations will flood the memory and cause lots of GC.
     * The MediaFile will be closed (and so destroyed from disk) when the image source is closed.
     *
     */

    // mxc://matrix.org/tVkMueateXaccEDyuezIdTkZ
    private suspend fun fetchFile(
        mediaSource: MediaSource,
        kind: MediaRequestData.Kind.File
    ): FetchResult? {
        return null
        /*   return mediaLoader.getMedia(mediaSource.url)
               .map { platformMedia ->
                   val byteArray: ByteArray = platformMedia.toByteArray()
                   val fileSystem = defaultFileSystem
                   SourceFetchResult(
                       source = ImageSource(
                           file = fileSystem.createDirectory().toOkioPath(),
                           fileSystem =,
                           closeable = byteArray
                       ),
                       mimeType = null,
                       dataSource = DataSource.DISK
                   )
               }
               .onFailure {
                   Napier.e("fetchFile failed:$it")
               }
               .getOrNull()*/
    }

    private suspend fun fetchContent(
        mediaSource: MediaSource,
        options: Options
    ): SourceFetchResult? {
        return mediaService.getMedia(
            uri = mediaSource.url,
        ).map { platformMedia ->
            platformMedia.toByteArray().asSourceResult()
        }.onFailure {
            Napier.e("fetchContent failed:$it")
        }.getOrNull()
    }

    private suspend fun fetchThumbnail(
        mediaSource: MediaSource,
        kind: MediaRequestData.Kind.Thumbnail,
        options: Options
    ): SourceFetchResult? {

        return mediaService.getThumbnail(
            uri = mediaSource.url,
            width = kind.width,
            height = kind.height,
        ).map { byteArray ->
            byteArray.toByteArray().asSourceResult()
        }.onFailure {
            Napier.e("getThumbnail failed:$it")
        }.getOrNull()
    }

    private fun ByteArray.asSourceResult(): SourceFetchResult {
        val bufferedSource = Buffer().write(this)
        val fileSystem: FileSystem = platformFileSystem
        return SourceFetchResult(
            source = ImageSource(source = bufferedSource, fileSystem = fileSystem),
            mimeType = null,
            dataSource = DataSource.NETWORK
        )

    }


}
