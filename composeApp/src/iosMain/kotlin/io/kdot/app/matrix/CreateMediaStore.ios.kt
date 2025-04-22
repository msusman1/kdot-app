package io.kdot.app.matrix

import net.folivo.trixnity.client.media.okio.OkioMediaStore
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformCreateMediaStoreModule(): Module = module {
    single<CreateMediaStore> {
        val rootPath = get<RootPath>()
        CreateMediaStore {
            OkioMediaStore(rootPath.resolveMedia())
        }
    }
}

