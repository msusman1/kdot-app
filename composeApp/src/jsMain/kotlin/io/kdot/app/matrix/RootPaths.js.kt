package io.kdot.app.matrix

import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformPathsModule(): Module = module {
    single<RootPath> {
        val rootPath = "/vfs".toPath()
        RootPath(rootPath)
    }
}
