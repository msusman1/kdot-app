package io.kdot.app.matrix

import io.kdot.app.getOs
import okio.Path
import okio.Path.Companion.toPath
import org.koin.core.module.Module
import org.koin.dsl.module

actual fun platformPathsModule(): Module = module {
    single<RootPath> {
        RootPath(getRootPath())
    }
}

private fun getRootPath(): Path {
    val appId = "io.kdot.app"
    return System.getenv("TRIXNITY_MESSENGER_ROOT_PATH")?.toPath()
        ?: when (getOs()) {
            io.kdot.app.OS.MAC_OS -> {
                System.getenv("HOME").toPath()
                    .resolve("Library")
                    .resolve("Application Support")
                    .resolve(appId)
            }

            io.kdot.app.OS.WINDOWS -> {
                System.getenv("LOCALAPPDATA").toPath()
                    .resolve(appId)
            }

            io.kdot.app.OS.LINUX -> {
                val dataHome = System.getenv("XDG_DATA_HOME")?.toPath()
                    ?: System.getenv("HOME").toPath().resolve(".local").resolve("share")

                dataHome.resolve(appId)
            }
        }
}