package io.kdot.app.libraries.matrixui.media

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.request.crossfade
import net.folivo.trixnity.client.MatrixClient

interface ImageLoaderFactory {
    fun create(context: PlatformContext, matrixClient: MatrixClient): ImageLoader
}


class ImageLoaderFactoryImpl : ImageLoaderFactory {
    override fun create(context: PlatformContext, matrixClient: MatrixClient): ImageLoader {
        return ImageLoader.Builder(context)
            .crossfade(true)
            .components {
                add(AvatarDataKeyer())
                add(MediaRequestDataKeyer())
                add(AvatarDataFetcherFactory(matrixClient))
                add(MediaRequestDataFetcherFactory(matrixClient))
            }
            .build()
    }

}