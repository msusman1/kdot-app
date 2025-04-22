package io.kdot.app.matrix

import okio.Path
import org.koin.core.module.Module
import kotlin.jvm.JvmInline

@JvmInline
value class RootPath(val path: Path) {
    fun resolveDatabase() = path.resolve("database")
    fun resolveMedia() = path.resolve("media")
}

expect fun platformPathsModule(): Module