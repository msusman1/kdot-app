package io.kdot.app.matrix

import net.folivo.trixnity.client.MatrixClient

class MatrixClientProvider {
    private var client: MatrixClient? = null

    fun setClient(matrixClient: MatrixClient) {
        client = matrixClient
    }

    fun getClient(): MatrixClient {
        return client ?: throw IllegalStateException("MatrixClient not initialized")
    }

    fun isInitialized(): Boolean = client != null
}