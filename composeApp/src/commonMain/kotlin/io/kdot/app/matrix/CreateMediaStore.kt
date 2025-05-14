package io.kdot.app.matrix

import net.folivo.trixnity.client.media.MediaStore
import okio.FileSystem
import org.koin.core.module.Module

fun interface CreateMediaStore {
    suspend operator fun invoke(): MediaStore
}

expect fun platformCreateMediaStoreModule(): Module

expect val platformFileSystem: FileSystem