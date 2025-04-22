package io.kdot.app.matrix

import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module
import platform.Foundation.NSDocumentDirectory
import platform.Foundation.NSSearchPathForDirectoriesInDomains
import platform.Foundation.NSUserDomainMask

actual fun platformPathsModule(): Module = module {
    single<RootPath> {
        val rootPath = NSSearchPathForDirectoriesInDomains(
            NSDocumentDirectory,
            NSUserDomainMask,
            true
        )[0] as String
        val path = rootPath.toPath()
        RootPath(path)
    }
}