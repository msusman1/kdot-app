package io.kdot.app.matrix

import io.ktor.client.HttpClient
import io.ktor.http.Url
import net.folivo.trixnity.client.MatrixClient
import net.folivo.trixnity.client.fromStore
import net.folivo.trixnity.client.loginWithPassword
import net.folivo.trixnity.clientserverapi.model.authentication.IdentifierType
import net.folivo.trixnity.core.model.UserId

interface MatrixClientFactory {

    suspend fun createFromLogin(userName: String, password: String): Result<MatrixClient>
    suspend fun createFromStore(userId: UserId): Result<MatrixClient?>
}

class MatrixClientFactoryImpl(
    private val createMediaStore: CreateMediaStore,
    private val createRepositoriesModule: CreateRepositoriesModule
) : MatrixClientFactory {
    override suspend fun createFromLogin(userName: String, password: String): Result<MatrixClient> {
        return MatrixClient.Companion.loginWithPassword(
            baseUrl = Url("https://matrix.org"),
            identifier = IdentifierType.User(userName),
            password = password,
            deviceId = null,
            repositoriesModuleFactory = { createRepositoriesModule.create(null) },
            mediaStoreFactory = { createMediaStore() },
            initialDeviceDisplayName = null,
            configuration = {
                httpClientEngine = HttpClient().engine
            }
        )
    }

    override suspend fun createFromStore(userId: UserId): Result<MatrixClient?> {
        return runCatching {
            MatrixClient.fromStore(
                repositoriesModule = createRepositoriesModule.load(null),
                mediaStore = createMediaStore(),
                configuration = {
                    httpClientEngine = HttpClient().engine
                }
            ).getOrThrow()
        }
    }

}
