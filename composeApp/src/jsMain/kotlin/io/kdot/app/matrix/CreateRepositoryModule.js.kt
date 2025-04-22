package io.kdot.app.matrix

import net.folivo.trixnity.client.store.repository.indexeddb.createIndexedDBRepositoriesModule
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformCreateRepositoriesModule(): Module = module {
    single<CreateRepositoriesModule> {
        val rootPath = get<RootPath>()
        object : CreateRepositoriesModule {
            override suspend fun generateDatabaseKey(): ByteArray? = null
            override suspend fun create(databaseKey: ByteArray?): Module = createInternal()
            override suspend fun load(databaseKey: ByteArray?): Module = createInternal()

            private suspend fun createInternal(): Module =
                createIndexedDBRepositoriesModule(
                    rootPath.resolveDatabase().resolve(DB_NAME).toString()
                )
        }
    }
}