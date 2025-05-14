package io.kdot.app.matrix

import coil3.SingletonImageLoader
import io.kdot.app.libraries.matrixui.media.ImageLoaderFactory
import net.folivo.trixnity.client.MatrixClient

class MatrixClientProvider(private val imageLoaderFactory: ImageLoaderFactory) {
    private var client: MatrixClient? = null

    fun setClient(matrixClient: MatrixClient) {
        client = matrixClient
        return SingletonImageLoader.setSafe { context ->
            imageLoaderFactory.create(context, matrixClient)
        }
    }

    fun getClient(): MatrixClient {
        return client ?: throw IllegalStateException("MatrixClient not initialized")
    }

    fun isInitialized(): Boolean = client != null
}