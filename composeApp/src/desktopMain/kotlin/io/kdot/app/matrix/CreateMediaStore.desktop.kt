package io.kdot.app.matrix

import net.folivo.trixnity.client.media.okio.OkioMediaStore
import okio.FileSystem
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformCreateMediaStoreModule(): Module = module {
    single<CreateMediaStore> {
        val path = get<RootPath>()
        CreateMediaStore {
            FileSystem.SYSTEM.createDirectories(path.path)
            OkioMediaStore(path.resolveMedia())
        }
    }
}


